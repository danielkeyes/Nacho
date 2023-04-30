package dev.danielkeyes.nacho

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import dev.danielkeyes.nacho.composables.MyScaffold
import dev.danielkeyes.nacho.resources.Soundbite
import dev.danielkeyes.nacho.resources.soundbites
import dev.danielkeyes.nacho.ui.theme.SoundBoardTheme
import dev.danielkeyes.nacho.utils.MyMediaPlayer

const val FULL_SIZE_BACKGROUND = R.drawable.nacho_libre_flying_solo

class SoundBoardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        val soundbites = soundbites
        val background = FULL_SIZE_BACKGROUND

        return ComposeView(requireContext()).apply {
            setContent {
                val context = LocalContext.current

                SoundBoardTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                    ) {
                        SoundBoard(
                            findNavController(),
                            soundbites = soundbites,
                            background = background,
                            playMedia = {
                                MyMediaPlayer.playSoundID(it, context)
                            },
                        )
                    }
                }
            }
        }
    }

    override fun onPause() {
        MyMediaPlayer.stopPlaying()
        super.onPause()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SoundBoard(
    navController: NavController,
    soundbites: List<Soundbite>,
    background: Int,
    playMedia: (Int) -> Unit,
) {
    val columns = 2

    val dropDownOptions = listOf<Pair<String, () -> Unit>>(
        Pair("Update Widget(s)") { navController.navigate(R.id.updateWidgetFragment) }
    )

    MyScaffold(dropDownOptions = dropDownOptions) { it ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Image(
                painter = painterResource(id = background),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Column(Modifier.fillMaxSize()) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(columns),
                    modifier = Modifier.weight(1f)
                ) {
                    items(soundbites) { soundbite ->
                        SoundbiteButton(soundbite = soundbite,
                            playMedia = { playMedia(soundbite.resourceId) })
                    }
                }
            }
        }
    }
}


@Composable
fun SoundbiteButton(
    soundbite: Soundbite,
    playMedia: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .alpha(.8f),
            onClick = {
                playMedia(soundbite.resourceId)
            },
            border = BorderStroke(width = 4.dp, color = MaterialTheme.colors.primaryVariant)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = soundbite.name.capitalize(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SoundbitePreview() {
    SoundBoardTheme {
        Column {
            SoundbiteButton(soundbite = Soundbite("Sound 1", 1), {})
            SoundbiteButton(soundbite = Soundbite("Second Sound", 1), {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SoundBoardTheme {
        SoundBoard(rememberNavController(), soundbites, FULL_SIZE_BACKGROUND, {})
    }
}


