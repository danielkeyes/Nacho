package dev.danielkeyes.nacho.utils

import android.content.Context
import android.media.MediaPlayer

object NachoMediaPlayer {

    private var mediaPlayer = MediaPlayer()

    init {
       nachoLog("Nacho Media Player init")
    }

//    private var instance: NachoMediaPlayer? = null
//    private var mediaPlayer = MediaPlayer()
//
//    fun getInstance(): NachoMediaPlayer? {
//        if (instance == null) {
//            synchronized(NachoMediaPlayer::class.java) {
//                if (instance == null) {
//                    instance = NachoMediaPlayer()
//                }
//            }
//        }
//        return instance
//    }

//    fun playSound(name: String, context: Context){
//        stopPlaying()
//        mediaPlayer = MediaPlayer.create(context, 3)
//        mediaPlayer.start()
//    }

    fun playSoundID(id: Int, context: Context){
        stopPlaying()
        mediaPlayer.release()
        mediaPlayer = MediaPlayer.create(context, id)
        mediaPlayer.start()
    }

    fun stopPlaying() {
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
    }
}