package dev.danielkeyes.nacho

import android.annotation.SuppressLint
import android.app.Application
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.danielkeyes.nacho.resources.SoundByte
import dev.danielkeyes.nacho.resources.WidgetBackground
import dev.danielkeyes.nacho.resources.nachoBackgrounds
import dev.danielkeyes.nacho.resources.nachoSoundBytes
import dev.danielkeyes.nacho.utils.SharedPreferencesHelper
import dev.danielkeyes.nacho.utils.nachoLog
import dev.danielkeyes.nacho.widget.NachoSoundByteWidget

class WidgetViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val _widgets: MutableLiveData<List<NachoWidget>> = MutableLiveData(listOf())
    val widgets: LiveData<List<NachoWidget>> = _widgets

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    // list of widgets at a certain time
    fun getWidgets(): List<NachoWidget>? {
        return widgets.value
    }

    init {
        nachoLog("WidgetViewModel: init")
        refreshWidgets()
    }

    private fun getWidgetIDs(): List<Int> {
        nachoLog("WidgetViewModel: getWidgetIDs")
        val widgetManager = AppWidgetManager.getInstance(context)
            .getAppWidgetIds(ComponentName(context, NachoSoundByteWidget::class.java))
        nachoLog("Widget total: ${widgetManager.size}")
        widgetManager.forEach { nachoLog(it.toString()) }
        return widgetManager.toList()
    }

    fun refreshWidgets() {
        nachoLog("WidgetViewModel: refreshWidgets")
        _isLoading.value = true
        val widgetIds = getWidgetIDs()
        val widgets = mutableListOf<NachoWidget>()
        widgetIds.forEach { widgetId ->
            val background =
                SharedPreferencesHelper.getBackground(widgetId, context, nachoBackgrounds.first())
            val soundByte =
                SharedPreferencesHelper.getSoundByte(widgetId, context, nachoSoundBytes.first())
            widgets.add(NachoWidget(widgetId, background, soundByte))
        }
        _widgets.postValue(widgets)
        _isLoading.value = false
    }

    fun updateWidget(widgetId: Int, background: WidgetBackground, soundByte: SoundByte) {

    }

    fun updateWidgetBackground(widgetId: Int, background: WidgetBackground) {
        nachoLog("WidgetViewModel: updateWidgetBackground")
        nachoLog("widgetId: $widgetId backgroundName: ${background.name}")

        SharedPreferencesHelper.setBackground(widgetId, background, context)

        val list = mutableListOf<NachoWidget>()
        _widgets.value?.forEach {
            if (it.widgetId == widgetId) {
                it.background = background
            }
            list.add(it.copy())
        }
        _widgets.value = list
    }

    fun updateWidgetSoundByte(widgetId: Int, soundByte: SoundByte) {
        nachoLog("WidgetViewModel: updateWidgetSoundByte")
        nachoLog("widgetId: $widgetId soundByteName: ${soundByte.name}")

        SharedPreferencesHelper.setSoundByte(widgetId, soundByte, context)

        nachoLog("before")
        widgets.value?.forEach {
            nachoLog(it.toString())
        }

        val list = mutableListOf<NachoWidget>()
        _widgets.value?.forEach {
            if (it.widgetId == widgetId) {
                it.soundByte = soundByte
            }
            list.add(it.copy())
        }
        _widgets.postValue(list)

        nachoLog("after")
        widgets.value?.forEach {
            nachoLog(it.toString())
        }
//        _widgets.notifyObserver()
    }

    //list += anotherList to use
    operator fun <T> MutableLiveData<ArrayList<T>>.plusAssign(values: List<T>) {
        val value = this.value ?: arrayListOf()
        value.addAll(values)
        this.value = value
    }

    private fun forceRefreshLiveData(){
        val list = mutableListOf<NachoWidget>()
        _widgets.value?.forEach {
            list.add(it.copy())
        }
        _widgets.value = list
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        val list: MutableLiveData<T>
        this.value
        this.postValue(this.value)
    }

    fun updateWidgets() {
        dev.danielkeyes.nacho.widget.updateWidgets(
            appWidgetIds = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(ComponentName(context, NachoSoundByteWidget::class.java)),
            context = context,
            appWidgetManager = AppWidgetManager.getInstance(context)
        )
    }
}

data class NachoWidget(
    val widgetId: Int,
    var background: WidgetBackground,
    var soundByte: SoundByte
) {
    override fun toString(): String {
        return "NachoWidget(widgetId=$widgetId, background=$background, soundByte=$soundByte)"
    }
}