package dev.danielkeyes.nacho

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import dev.danielkeyes.nacho.ui.theme.NachoTheme
import java.lang.reflect.Field


class MainActivity : ComponentActivity() {

    private var mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NachoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Content(mediaPlayer) {
                        mediaPlayer.stopPlaying()
                        mediaPlayer = it
                    }
                }
            }
        }
    }

    override fun onPause() {
        mediaPlayer.stopPlaying()
        super.onPause()
    }

    private fun MediaPlayer.stopPlaying(){
        if(this.isPlaying) this.stop()
    }
}

@Composable
fun Content(mediaPlayer: MediaPlayer, updateMediaPlayer: (MediaPlayer) -> Unit) {
    val context = LocalContext.current

    // images and webkit ...... <--list contents
//    val allAssets = context.assets.list("")
//    allAssets.logAll("allAssets")

    context.assets.list("raw").logAll("raw")

    var mediaPlayer = MediaPlayer()
//    val a = context.assets.openFd("/raw/nacho_libre_about_the_gospel.mp3")

    val nachoLibreSounds = listRaw().filter { it.name.contains("nacho") }
//    Image(
//        painter = painterResource(id = R.drawable.nachoflyingsolo),
//        contentDescription = "",
//        contentScale = ContentScale.Fit
//    )

    Column() {
        nachoLibreSounds.forEach{
            Button(onClick = {
                if(mediaPlayer.isPlaying) mediaPlayer.stop()
                updateMediaPlayer(MediaPlayer.create(context, it.getInt(it)))
                mediaPlayer.setOnPreparedListener {
                    mediaPlayer.start()
                }
            }) {
                Text(text = "${it.name.convertFromRawNachoSoundName()}")
            }
        }
    }
}

fun logNullCheck(any: Any?, name: String) {
    if(any == null) {
        Log.e("nacho","$name is null")
    } else {
        Log.e("nacho","$name is not null")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NachoTheme {
        Content(MediaPlayer()){}
    }
}

fun Array<String?>?.logAll(name: String) {
    if (this == null || this.isNotEmpty()){
        Log.e("nacho", "$name is null or empty")
    } else {
        Log.e("nacho","$name contains")
        this.forEach {
            Log.e("nacho","$it")
        }
    }
}
data class NachoSound(val name: String, val id: Int)
fun listRaw(): Array<Field> {
    val fields: Array<Field> =
        R.raw::class.java.fields
    fields.forEach {field ->
        Log.d(
            "nacho", "name=${field.name} id=${field.getInt(field)}",
        ) }
    return fields
}

fun String.convertFromRawNachoSoundName(): String{
    return this.removePrefix("nacho_libre").replace('_', ' ')
}
