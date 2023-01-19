package dev.danielkeyes.nacho.utils

import android.content.Context
import android.media.MediaPlayer

/**
 * Controls media playback
 */
object MyMediaPlayer {
    private var mediaPlayer = MediaPlayer()

    /**
     * Plays sound with [id]. Built in support for only playing one sound at a time
     */
    fun playSoundID(id: Int, context: Context){
        stopPlaying()
        mediaPlayer.reset()
        mediaPlayer.release()
        mediaPlayer = MediaPlayer.create(context, id)
        mediaPlayer.start()
    }

    /**
     * Stops current sound playback
     */
    fun stopPlaying() {
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
    }
}