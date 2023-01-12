package dev.danielkeyes.nacho

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import dev.danielkeyes.nacho.resources.SoundByte
import dev.danielkeyes.nacho.ui.theme.NachoTheme
import dev.danielkeyes.nacho.utils.NachoMediaPlayer
import dev.danielkeyes.nacho.utils.SoundByteUtils
import kotlin.random.Random

class SoundBoardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        return ComposeView(requireContext()).apply {
            setContent {
                val context = LocalContext.current

                NachoTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        Content(
                            nachoSounds = SoundByteUtils.getSoundBytes("nacho_libre"),
                            playMedia = {
                                NachoMediaPlayer.playSoundID(it, context)
                            },
                            stopMedia = {
                                NachoMediaPlayer.stopPlaying()
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onPause() {
        NachoMediaPlayer.stopPlaying()
        super.onPause()
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Content(nachoSounds: List<SoundByte>, playMedia: (Int) -> Unit, stopMedia: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.nachoflyingsolo),
        contentDescription = "",
        contentScale = ContentScale.Crop
    )

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2)
    ) {
        items(nachoSounds) { soundByte ->

            var favorite: Boolean by rememberSaveable { mutableStateOf(Random.nextBoolean()) }
            SoundByteButton(soundByte = soundByte,
                isFavorite = favorite,
                favorite = { it -> favorite = it },
                playMedia = { playMedia(soundByte.resourceId)}
            )
        }
    }
}

@Composable
fun SoundByteButton(soundByte: SoundByte, isFavorite: Boolean, favorite: (Boolean) -> Unit, playMedia: (Int) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(.8f),
            onClick = {
                playMedia(soundByte.resourceId)
            }) {
            Text(
                modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                text = soundByte.name.capitalize(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }

        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
            "favorite",
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .clickable { favorite(!isFavorite) }
                .padding(8.dp),
            tint = Color(0xFFFFD700)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun SoundBytePreview() {
    NachoTheme {
        Column {
            SoundByteButton(
                soundByte = SoundByte("anaconda squeeze", 1),
                isFavorite = true,
                favorite = {},
                playMedia = {})
            SoundByteButton(
                soundByte = SoundByte("get that corn", 1),
                isFavorite = false,
                favorite = {},
                playMedia = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NachoTheme {
        Content(SoundByteUtils.getSoundBytes("nacho_libre"), {}, {})
    }
}
fun String.sanitizeSoundByteName(prefixIdentifier: String): String {
    return this.removePrefix(prefixIdentifier).replace('_', ' ').trimStart()
}



