package dev.danielkeyes.nacho.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import dev.danielkeyes.nacho.R
import dev.danielkeyes.nacho.utils.NachoMediaPlayer
import dev.danielkeyes.nacho.utils.SoundByteUtils

class NachoSoundByteWidget : AppWidgetProvider() {

    private val PLAY_CLICKED = "playButtonClick"
    private val SETTINGS_CLICKED = "settingsButtonClick"

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("nacho", "intent.action: ${intent.action}")
        super.onReceive(context, intent)
        if (PLAY_CLICKED == intent.action) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val remoteViews = RemoteViews(context.packageName, R.layout.nacho_soundbyte_widget)
            val nachoSoundByteWidget = ComponentName(context, NachoSoundByteWidget::class.java)

            // TODO play correct sound
            NachoMediaPlayer.playSoundID(SoundByteUtils.getSoundByte("wanna").id, context)

            Toast.makeText(context, "hello", Toast.LENGTH_LONG).show()
            // UPDATE WIDGET - possibly uneccessary in my case
            appWidgetManager.updateAppWidget(nachoSoundByteWidget, remoteViews)
        } else if (SETTINGS_CLICKED == intent.action) {
            // TODO: launch to updateWidget Page
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // TODO have it get the correct background and soundByte
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.nacho_soundbyte_widget)
            views.setTextViewText(R.id.soundbyte_name_tv, "Get that corn")
            views.setOnClickPendingIntent(R.id.play_ib, getPendingSelfIntent(context, PLAY_CLICKED))
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private fun getPendingSelfIntent(context: Context?, action: String?): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action

        val flags =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }

        return PendingIntent.getBroadcast(context, 0, intent, flags)

    }
}

// TODO does this need to be public so I can call it from the WidgetUpdate composable?

//    fun updateAppWidget(
//        context: Context,
//        appWidgetManager: AppWidgetManager,
//        appWidgetId: Int
//    ) {
//        // get sound ID using widgetId from shared preferences
//        // get sound name reference
//        // set sound name
//        // set sound name on click
//
////    val lmp = SharedPreferencesHelper().getFirstDayLastMenstrualPeriod(context)
////    val currentDate = System.currentTimeMillis()
//
////    val widgetText = context.getString(R.string.appwidget_text)
//        // Construct the RemoteViews object
//        val views = RemoteViews(context.packageName, R.layout.nacho_soundbyte_widget)
//        views.setTextViewText(R.id.soundbyte_name_tv, "Get that corn")
////        views.setOnClickPendingIntent(R.id.play_ib, getPendingSelfIntent(context, PLAY_CLICKED))
//
////    views.setTextViewText(R.id.appwidget_text, DataHelper().getDifferenceString(lmp, currentDate))
////    views.setTextViewText(R.id.appwidget_text, "Something")
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views)
//    }
//
