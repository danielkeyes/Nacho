package dev.danielkeyes.nacho.widgetglance

//package dev.danielkeyes.nacho.widget
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.glance.action.clickable
//import androidx.glance.appwidget.GlanceAppWidget
//import androidx.glance.appwidget.GlanceAppWidgetReceiver
//import androidx.glance.appwidget.action.actionRunCallback
//import androidx.glance.background
//import androidx.glance.GlanceId
//import androidx.glance.GlanceModifier
//import androidx.glance.layout.fillMaxSize
//import androidx.glance.layout.padding
//import androidx.glance.text.Text
//import androidx.glance.text.TextAlign
//import androidx.glance.text.TextStyle
//import androidx.glance.unit.ColorProvider
//import android.content.Context
//import androidx.glance.action.ActionParameters
//import androidx.glance.appwidget.action.ActionCallback
//import dev.danielkeyes.nacho.utils.NachoMediaPlayer
//import dev.danielkeyes.nacho.utils.SoundByteUtils


// Glance - compose widget - underwhelming.....
// Still requires an XML for preview of widget.....what is the point....
//    implementation "androidx.glance:glance-appwidget:1.0.0-alpha05"
// Not actually available
//    implementation "androidx.glance:glance-appwidget-preview:1.0.0-alpha05"
//    implementation "androidx.glance:glance-preview:1.0.0-alpha05"


//
//class SoundByteWidget: GlanceAppWidget() {
//    @Composable
//    override fun Content() {
//        SoundByteWidgetContent()
//    }
//
//    companion object {
//        const val SOUNDBYTE_WIDGET_PREFS_KEY = "SOUNDBYTE_WIDGET_PREFS_KEY"
//    }
//}
//
//class SoundByteReceiver: GlanceAppWidgetReceiver() {
//    override val glanceAppWidget: GlanceAppWidget = SoundByteWidget()
//}
//
//@Composable
//fun SoundByteWidgetContent() {
//    Text(
//        text = "I wanna win",
//        style = TextStyle(
//            color = ColorProvider(Color(0xFFFFFFFF)),
//            fontSize = 18.sp,
//            textAlign = TextAlign.Center
//            ),
//        modifier = GlanceModifier
//            .clickable(onClick = actionRunCallback<PlaySoundByteAction>())
//            .background(Color(0xFF000000))
//            .fillMaxSize()
//            .padding(8.dp)
//    )
//}
//
//
//class changeSoundByteAction: ActionCallback {
//    override suspend fun onAction(
//        context: Context,
//        glanceId: GlanceId,
//        parameters: ActionParameters
//    ) {
//        TODO("Not yet implemented")
//    }
//}
//
//class PlaySoundByteAction: ActionCallback {
//    override suspend fun onAction(
//        context: Context,
//        glanceId: GlanceId,
//        parameters: ActionParameters
//    ) {
//        val soundByte = SoundByteUtils.getSoundByte("win")
//        NachoMediaPlayer.playSoundID(soundByte.id, context)
//    }
//}