package dev.danielkeyes.nacho.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import dev.danielkeyes.nacho.R
import dev.danielkeyes.nacho.resources.getSoundByte
import dev.danielkeyes.nacho.resources.nachoBackgrounds
import dev.danielkeyes.nacho.resources.nachoSoundBytes
import dev.danielkeyes.nacho.utils.NachoMediaPlayer
import dev.danielkeyes.nacho.utils.SharedPreferencesHelper


private const val PLAY_CLICKED = "playButtonClick"
private const val SETTINGS_CLICKED = "settingsButtonClick"
private const val SOUNDBYTE_EXTRA = "soundByteExtra"

private val flags =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }

class NachoSoundByteWidget : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("nacho", "intent.action: ${intent.action}")
        super.onReceive(context, intent)
        if (PLAY_CLICKED == intent.action) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val remoteViews = RemoteViews(context.packageName, R.layout.nacho_soundbyte_widget)
            val nachoSoundByteWidget = ComponentName(context, NachoSoundByteWidget::class.java)

            val soundByteId = intent.getIntExtra("soundByteExtra", nachoSoundBytes.first().resourceId)
            NachoMediaPlayer.playSoundID(nachoSoundBytes.getSoundByte(soundByteId).resourceId, context)

            // TODO: Do I need to do both of these?
            onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(nachoSoundByteWidget))
            appWidgetManager.updateAppWidget(nachoSoundByteWidget, remoteViews)
         } else if (SETTINGS_CLICKED == intent.action) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val remoteViews = RemoteViews(context.packageName, R.layout.nacho_soundbyte_widget)
            val nachoSoundByteWidget = ComponentName(context, NachoSoundByteWidget::class.java)

            // TODO: launch to updateWidget Page

            // TODO: Do I need to do both of these?
            onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(nachoSoundByteWidget))
            appWidgetManager.updateAppWidget(nachoSoundByteWidget, remoteViews)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // TODO have it get the correct background and soundByte
        // There may be multiple widgets active, so update all of them
        updateWidgets(appWidgetIds, context, appWidgetManager)
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        if( context != null && appWidgetManager != null) {
            onUpdate(context, appWidgetManager, IntArray(appWidgetId))
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

fun updateWidgets(
    appWidgetIds: IntArray,
    context: Context,
    appWidgetManager: AppWidgetManager
) {
    for (appWidgetId in appWidgetIds) {
        val views = RemoteViews(context.packageName, R.layout.nacho_soundbyte_widget)

        val background = SharedPreferencesHelper.getBackground(appWidgetId, context, nachoBackgrounds.first())
        val soundByte = SharedPreferencesHelper.getSoundByte(appWidgetId, context, nachoSoundBytes.first())

        // Set WidgetText
        views.setTextViewText(R.id.soundbyte_name_tv, soundByte.name)

        // Set Background
        views.setImageViewResource(R.id.widget_background_iv, background.resourceId)

        // Play onClick
        val playIntent = Intent(context, NachoSoundByteWidget::class.java)
        playIntent.action = PLAY_CLICKED
        playIntent.putExtra(SOUNDBYTE_EXTRA, soundByte.resourceId)
        val playPendingIntent = PendingIntent.getBroadcast(context,0,playIntent, flags)
        views.setOnClickPendingIntent(R.id.play_ib, playPendingIntent)

        // Settings onClick
        val settingsIntent = Intent(context, NachoSoundByteWidget::class.java)
        settingsIntent.action = SETTINGS_CLICKED
        val settingsPendingIntent = PendingIntent.getBroadcast(context,0,settingsIntent, flags)
        views.setOnClickPendingIntent(R.id.settings_ib, getPendingSelfIntent(
            context,
            SETTINGS_CLICKED
        ))

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}

private fun getPendingSelfIntent(context: Context?, action: String?, soundByteId: Int = -1): PendingIntent? {
    val intent = Intent(context, NachoSoundByteWidget::class.java)
    intent.action = action
    intent.putExtra(SOUNDBYTE_EXTRA, soundByteId)

    return PendingIntent.getBroadcast(context, 0, intent, flags)
}