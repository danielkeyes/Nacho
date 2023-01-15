package dev.danielkeyes.nacho

import android.annotation.SuppressLint
import android.app.Application
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import dev.danielkeyes.nacho.resources.*
import dev.danielkeyes.nacho.utils.NachoMediaPlayer
import dev.danielkeyes.nacho.utils.SharedPreferencesHelper
import dev.danielkeyes.nacho.utils.nachoLog
import dev.danielkeyes.nacho.widget.NachoSoundByteWidget
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WidgetViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val _widgets: MutableLiveData<List<NachoWidget>> = MutableLiveData(listOf())
    val widgets: LiveData<List<NachoWidget>> = _widgets

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        refreshWidgets()
    }

    private fun getWidgetIDs(): List<Int> {
        val widgetIds = AppWidgetManager.getInstance(context)
            .getAppWidgetIds(ComponentName(context, NachoSoundByteWidget::class.java))
        nachoLog("Widget total: ${widgetIds.size}")
        widgetIds.forEach {
            nachoLog(it.toString())
        }
        return widgetIds.toList()
    }

    fun refreshWidgets() {
        _isLoading.value = true

        val widgetIds = getWidgetIDs()

        val widgetsList = mutableListOf<NachoWidget>()
        widgetIds.forEach { widgetId ->
            val background =
                SharedPreferencesHelper.getBackground(widgetId, context, nachoBackgrounds.first())
            val soundByte =
                SharedPreferencesHelper.getSoundByte(widgetId, context, nachoSoundBytes.first())
            widgetsList.add(NachoWidget(widgetId, background, soundByte))
        }

        _widgets.value = widgetsList.toList()

        // This helps the loading true -> false to not be to quick
        viewModelScope.launch {
            delay(1000)
            _isLoading.postValue(false)
        }
    }

    fun updateWidgetBackground(widgetId: Int, background: WidgetBackground) {
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