package dev.danielkeyes.nacho

import android.annotation.SuppressLint
import android.app.Application
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.danielkeyes.nacho.resources.SoundByte
import dev.danielkeyes.nacho.resources.WidgetBackground
import dev.danielkeyes.nacho.resources.nachoBackgrounds
import dev.danielkeyes.nacho.resources.nachoSoundBytes
import dev.danielkeyes.nacho.utils.NachoMediaPlayer
import dev.danielkeyes.nacho.utils.SharedPreferencesHelper
import dev.danielkeyes.nacho.utils.nachoLog
import dev.danielkeyes.nacho.widget.NachoSoundByteWidget

class WidgetViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val _widgets: MutableLiveData<List<NachoWidget>> = MutableLiveData(listOf())
    val widgets: LiveData<List<NachoWidget>> = _widgets

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

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
        _isLoading.postValue(true)

        val widgetIds = getWidgetIDs()

        val widgets = mutableListOf<NachoWidget>()
        widgetIds.forEach { widgetId ->
            val background =
                SharedPreferencesHelper.getBackground(widgetId, context, nachoBackgrounds.first())
            val soundByte =
                SharedPreferencesHelper.getSoundByte(widgetId, context, nachoSoundBytes.first())
            widgets.add(NachoWidget(widgetId, background, soundByte))
        }
        _widgets.postValue(widgets.toList())

        _isLoading.postValue(false)
    }

    fun updateWidgetBackground(widgetId: Int, background: WidgetBackground) {
        nachoLog("WidgetViewModel: updateWidgetBackground")
        nachoLog("widgetId: $widgetId backgroundName: ${background.name}")

        SharedPreferencesHelper.setBackground(widgetId, background, context)

        val list = mutableListOf<NachoWidget>()
        _widgets.value?.forEach {
            val a = NachoWidget(
                it.widgetId,
                it.background,
                it.soundByte
            )
            if(a.widgetId == widgetId){
                a.background = background
            }

            list.add(a)
        }
        _widgets.postValue(list)
    }

    fun updateWidgetSoundByte(widgetId: Int, soundByte: SoundByte) {
        nachoLog("WidgetViewModel: updateWidgetSoundByte")
        nachoLog("widgetId: $widgetId soundByteName: ${soundByte.name}")

        SharedPreferencesHelper.setSoundByte(widgetId, soundByte, context)

        val list = mutableListOf<NachoWidget>()
        _widgets.value?.forEach {
            val a = NachoWidget(
                it.widgetId,
                it.background,
                it.soundByte
            )
            if(a.widgetId == widgetId){
                a.soundByte = soundByte
            }

            list.add(a)
        }

        _widgets.postValue(list)
    }

    fun updateWidgets() {
        dev.danielkeyes.nacho.widget.updateWidgets(
            appWidgetIds = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(ComponentName(context, NachoSoundByteWidget::class.java)),
            context = context,
            appWidgetManager = AppWidgetManager.getInstance(context)
        )
    }

    fun playSoundByte(soundByte: SoundByte) {
        NachoMediaPlayer.playSoundID(soundByte.resourceId, context)
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