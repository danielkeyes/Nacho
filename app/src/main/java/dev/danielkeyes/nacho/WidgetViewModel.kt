package dev.danielkeyes.nacho

import android.annotation.SuppressLint
import android.app.Application
import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.danielkeyes.nacho.resources.*
import dev.danielkeyes.nacho.utils.MyMediaPlayer
import dev.danielkeyes.nacho.utils.SharedPreferencesHelper
import dev.danielkeyes.nacho.utils.myLog
import dev.danielkeyes.nacho.widget.SoundByteWidget
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WidgetViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    // Widget data is store in sharepreferences, but we dont listen for sharedpref changes
    // When widget change is made, update list and send shared pref update.
    // Not ideal but works without issue for our use case
    private val _widgets: MutableLiveData<List<MyWidgetDO>> = MutableLiveData(listOf())
    val widgets: LiveData<List<MyWidgetDO>> = _widgets

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        refreshWidgets()
    }

    private fun getWidgetIDs(): List<Int> {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context,
            SoundByteWidget::class.java))
        return widgetIds.toList()
    }

    fun refreshWidgets() {
        _isLoading.value = true

        val widgetIds = getWidgetIDs()

        val widgetsList = mutableListOf<MyWidgetDO>()
        widgetIds.forEach { widgetId ->
            val background =
                SharedPreferencesHelper.getBackground(widgetId, context, widgetBackgrounds.first())
            val soundByte =
                SharedPreferencesHelper.getSoundByte(widgetId, context, soundBytes.first())
            widgetsList.add(MyWidgetDO(widgetId, background, soundByte))
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

        val list = mutableListOf<MyWidgetDO>()
        _widgets.value?.forEach {
            val a = MyWidgetDO(
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

        val list = mutableListOf<MyWidgetDO>()
        _widgets.value?.forEach {
            val a = MyWidgetDO(
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
                .getAppWidgetIds(ComponentName(context, SoundByteWidget::class.java)),
            context = context,
            appWidgetManager = AppWidgetManager.getInstance(context)
        )
    }

    fun playSoundByte(soundByte: SoundByte) {
        MyMediaPlayer.playSoundID(soundByte.resourceId, context)
    }

    fun deleteAllWidgets(){
        val host = AppWidgetHost(context, 0)
        getWidgetIDs().forEach {
            host.deleteAppWidgetId(it)
        }
        refreshWidgets()
    }
}