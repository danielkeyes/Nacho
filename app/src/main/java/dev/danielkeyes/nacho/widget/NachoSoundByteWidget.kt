package dev.danielkeyes.nacho.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import dev.danielkeyes.nacho.R

class NachoSoundByteWidget : AppWidgetProvider() {

    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.hasExtra(MY_PREGNANCY_WIDGET_IDS_KEY)) {
            val ids = intent.extras?.getIntArray(MY_PREGNANCY_WIDGET_IDS_KEY)
            onUpdate((context)!!, AppWidgetManager.getInstance(context), (ids)!!)
        } else super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // get sound ID using widgetId from shared preferences
    // get sound name reference
    // set sound name
    // set sound name on click

//    val lmp = SharedPreferencesHelper().getFirstDayLastMenstrualPeriod(context)
//    val currentDate = System.currentTimeMillis()

//    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
//    val views = RemoteViews(context.packageName, R.layout.pregnancy_widget)
//    views.setTextViewText(R.id.appwidget_text, DataHelper().getDifferenceString(lmp, currentDate))
//    views.setTextViewText(R.id.appwidget_text, "Something")

    // Instruct the widget manager to update the widget
//    appWidgetManager.updateAppWidget(appWidgetId, views)
}
