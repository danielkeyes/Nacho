package dev.danielkeyes.nacho.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder
import dev.danielkeyes.nacho.MainActivity
import dev.danielkeyes.nacho.R
import dev.danielkeyes.nacho.resources.getSoundByte
import dev.danielkeyes.nacho.resources.widgetBackgrounds
import dev.danielkeyes.nacho.resources.soundBytes
import dev.danielkeyes.nacho.utils.MyMediaPlayer
import dev.danielkeyes.nacho.utils.SharedPreferencesHelper


private const val PLAY_CLICKED = "playButtonClick"
private const val SETTINGS_CLICKED = "settingsButtonClick"
private const val SOUNDBYTE_EXTRA = "soundByteExtra"
private const val WIDGET_ID = "widgetID"

private val flags =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }

class SoundByteWidget : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent) {
        if (PLAY_CLICKED == intent.action) {
            // Get soundByte and play it
            val soundByteId = intent.getIntExtra(SOUNDBYTE_EXTRA, soundBytes.first().resourceId)
            val soundByte = soundBytes.getSoundByte(soundByteId)
            MyMediaPlayer.playSoundID(soundByte.resourceId, context)

            // update widgets
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val widget = ComponentName(context, SoundByteWidget::class.java)
            onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(widget))
         }

        super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        updateWidgets(appWidgetIds, context, appWidgetManager)
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        if( context != null && appWidgetManager != null) {
            onUpdate(context, appWidgetManager, IntArray(appWidgetId))
        }
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
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
    appWidgetIds.forEach  { appWidgetId ->
        val views = RemoteViews(context.packageName, R.layout.soundbyte_widget_with_buttons)

        // retrieve widget Background and Soundbyte for appWidgetId
        val background = SharedPreferencesHelper.getBackground(appWidgetId, context, widgetBackgrounds.first())
        val soundByte = SharedPreferencesHelper.getSoundByte(appWidgetId, context, soundBytes.first())

        // Set soundbyte text
        views.setTextViewText(R.id.soundbyte_name_tv, soundByte.name)

        // Set background resource
        views.setImageViewResource(R.id.widget_background_iv, background.resourceId)

        // Set play button launch pending intent PLAY_CLICKED
        val playIntent = Intent(context, SoundByteWidget::class.java)
        playIntent.action = PLAY_CLICKED
        playIntent.putExtra(SOUNDBYTE_EXTRA, soundByte.resourceId)
        playIntent.putExtra(WIDGET_ID, appWidgetId)
        val playPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, playIntent, flags)
        views.setOnClickPendingIntent(R.id.play_ib, playPendingIntent)


        // Set setting button to navigate to updateWidgetFragment
        val pendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.updateWidgetFragment)
            .createPendingIntent()

        views.setOnClickPendingIntent(R.id.settings_ib, pendingIntent)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}