package dev.danielkeyes.nacho.utils

import android.content.Context
import android.media.MediaPlayer

object NachoMediaPlayer {

    private var mediaPlayer = MediaPlayer()

    init {
       nachoLog("Nacho Media Player init")
    }

    fun playSoundID(id: Int, context: Context){
        stopPlaying()
        mediaPlayer.reset()
        mediaPlayer.release()
        mediaPlayer = MediaPlayer.create(context, id)
        mediaPlayer.start()
    }

    fun stopPlaying() {
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
    }
}