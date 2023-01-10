//package dev.danielkeyes.nacho.widget
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.glance.GlanceModifier
//import androidx.glance.action.clickable
//import androidx.glance.appwidget.GlanceAppWidget
//import androidx.glance.appwidget.GlanceAppWidgetReceiver
//import androidx.glance.appwidget.action.actionRunCallback
//import androidx.glance.background
//import androidx.glance.layout.fillMaxSize
//import androidx.glance.layout.padding
//import androidx.glance.text.Text
//import androidx.glance.text.TextAlign
//import androidx.glance.text.TextStyle
//import androidx.glance.unit.ColorProvider
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