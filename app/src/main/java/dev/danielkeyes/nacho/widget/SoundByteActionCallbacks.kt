//package dev.danielkeyes.nacho.widget
//
//import android.content.Context
//import androidx.glance.GlanceId
//import androidx.glance.action.ActionParameters
//import androidx.glance.appwidget.action.ActionCallback
//import dev.danielkeyes.nacho.utils.NachoMediaPlayer
//import dev.danielkeyes.nacho.utils.SoundByteUtils
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