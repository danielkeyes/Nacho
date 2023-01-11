package dev.danielkeyes.nacho.composables

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue //TODO add back later when setting
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import dev.danielkeyes.nacho.R
import dev.danielkeyes.nacho.SoundByte
import dev.danielkeyes.nacho.ui.theme.NachoTheme

// TODO: Move to resource package ? Backgrounds
// add ability to get specific one by name, if null return first?
// can store name in shared preferences
data class Background(val name: String, val resource: Int)

val nachoBackgrounds: List<Background> =
    listOf(
        Background("Flying Nacho", R.drawable.nachoflyingsolo),
        Background("Flying Nacho", R.drawable.nachoflyingsolo),
        Background("Flying Nacho", R.drawable.nachoflyingsolo),
        Background("Flying Nacho", R.drawable.nachoflyingsolo),
        Background("Flying Nacho", R.drawable.nachoflyingsolo),
        Background("Flying Nacho", R.drawable.nachoflyingsolo),
        Background("Flying Nacho", R.drawable.nachoflyingsolo),
    )

// TODO: Move to resource package ? SoundByte
// add ability to get specific one by name, if null return first?
// can store name in shared preferences
val nachoSoundBytes: List<SoundByte> =
    listOf(
        SoundByte("I wanna win", 1),
        SoundByte("Anaconda Squeeze", 1),
        SoundByte("Dang exciting", 1),
        SoundByte("Fantastic", 1),
        SoundByte("Fancy ladies", 1),
        SoundByte("Floozy", 1),
        SoundByte("Get that corn", 1),
        SoundByte("How did you find me", 1),
        SoundByte("Go away", 1),
        SoundByte("Listen to me", 1),
        SoundByte("My life it good", 1),
        SoundByte("Punch to the face", 1),
        SoundByte("Salvation and stuff", 1),
        SoundByte("Stretchy Pants", 1),
        SoundByte("Take it easy", 1),
        SoundByte("Sucks to be me", 1),
        SoundByte("Wrestle your neighbor", 1),
        SoundByte("Really long fake name for testing", 1),
    )

@Composable
fun WidgetConfig() {
    val background: Background by rememberSaveable { mutableStateOf(nachoBackgrounds.first()) }
    val soundByte: SoundByte by rememberSaveable { mutableStateOf(nachoSoundBytes.first()) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "WidgetPreview")
        WidgetPreview(background, soundByte)
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Text(text = "Select a background")
            BackgroundPicker()
            Text(text = "Select a sound")
            SoundBytePicker()
        }
    }
}

@Composable
fun BackgroundPicker() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(items = nachoBackgrounds) { backGround ->
                Box(modifier = Modifier.padding(16.dp)) {
                    WidgetPreview(
                        background = backGround,
                        soundByte = nachoSoundBytes.first(),
                        modifier = Modifier.clickable{
//                        updateBackground(backGround) TODO
                        },
                        backgroundOnly = true
                    )
                }
            }
        }
    )
}

@Composable
fun SoundBytePicker() {
    Column() {
        nachoSoundBytes.forEach { soundByte ->
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .alpha(.8f),
                    onClick = {
                        // updateSound(soundByte)
                    }
                ) {
                Text(
                    modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                    text = soundByte.name,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Composable
fun WidgetPreview(
    background: Background,
    soundByte: SoundByte,
    modifier: Modifier = Modifier,
    backgroundOnly: Boolean = false
) {
    Box(modifier = modifier) {
        AndroidView(factory = { context ->
            val view =
                LayoutInflater.from(context).inflate(R.layout.nacho_soundbyte_widget, null, false)
            val soundByteNameTV = view.findViewById<TextView>(R.id.soundbyte_name_tv)
            val soundByteBackgroundIV = view.findViewById<ImageView>(R.id.widget_background_iv)
            val nameAndButtonLL = view.findViewById<LinearLayout>(R.id.name_and_buttons_ll)

            soundByteNameTV.text = soundByte.name
            soundByteBackgroundIV.setBackgroundResource(background.resource)
            if (backgroundOnly) {
                nameAndButtonLL.visibility = View.GONE
            }

            view
        },
            modifier = Modifier
                .width(144.dp)
                .height(144.dp),
            update = { view ->
                view.findViewById<TextView>(R.id.soundbyte_name_tv).text = soundByte.name
                view.findViewById<ImageView>(R.id.widget_background_iv)
                    .setBackgroundResource(background.resource)
            }
        )
    }
}

//about 72x72 for 1x1 widget
@Composable
fun Widget2x2(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .width(144.dp)
            .height(144.dp), content = content
    )
}

@Preview
@Composable
fun PreviewWidgetConfig() {
    NachoTheme {
        WidgetConfig()
    }
}

@Preview()
@Composable
fun PreviewWidgetPreview() {
    NachoTheme {
        WidgetPreview(nachoBackgrounds.first(), SoundByte("I wanna win", 1))
    }
}

@Preview
@Composable
fun PreviewBackgroundPicker() {
    NachoTheme {
        BackgroundPicker()
    }
}

@Preview
@Composable
fun PreviewSoundBytePicker() {
    NachoTheme {
        SoundBytePicker()
    }
}