package dev.danielkeyes.nacho

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import dev.danielkeyes.nacho.ui.theme.NachoTheme
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
//import androidx.compose.runtime.setValue //TODO add back later when setting
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import dev.danielkeyes.nacho.resources.SoundByte
import dev.danielkeyes.nacho.resources.WidgetBackground
import dev.danielkeyes.nacho.resources.nachoBackgrounds
import dev.danielkeyes.nacho.resources.nachoSoundBytes

class UpdateWidgetFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        return ComposeView(requireContext()).apply {
            setContent {
                NachoTheme() {
                    UpdateWidgetContent()
                }
            }
        }
    }
}

const val USE_COMPOSE_WIDGET_PREVIEW = true

@OptIn(ExperimentalPagerApi::class)
@Composable
fun UpdateWidgetContent() {

    // Need a ViewModel here
    val backgroundName by rememberSaveable { mutableStateOf(nachoBackgrounds.first().name) }
    val soundByteName by rememberSaveable { mutableStateOf(nachoSoundBytes.first().name) }

    val pagerState = rememberPagerState()
    val pages = listOf("Background", "SoundByte")

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Widget Preview")
        WidgetPreview(
            nachoBackgrounds.first { it.name == backgroundName },
            nachoSoundBytes.first { it.name == soundByteName },
            modifier = Modifier.padding(16.dp)
        )
        Column(Modifier.weight(1f)) {
            TabRow(
                // Our selected tab is our current page
                selectedTabIndex = pagerState.currentPage,
                // Override the indicator, using the provided pagerTabIndicatorOffset modifier
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                    )
                }
            ) {
                // Add tabs for all of our pages

                pages.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        onClick = { /* TODO */ },
                    )
                }
            }

            HorizontalPager(
                count = pages.size,
                state = pagerState,
            ) { page ->
                Column() {
                    if (pages[page] == "Background") {
                        BackgroundPicker()
                    } else {
                        SoundBytePicker()
                    }
                }
            }
        }
    }
}


//    HorizontalPager(count = 2)
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Text(text = "WidgetPreview")
//        WidgetPreview(
//            nachoBackgrounds.first { it.name == backgroundName },
//            nachoSoundBytes.first{it.name == soundByteName})
//        Column(Modifier.weight(1f)) {
//            Text(text = "Select a background")
//            BackgroundPicker()
//            Text(text = "Select a sound")
//            SoundBytePicker()
//        }
//    }


@Composable
fun BackgroundPicker() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(items = nachoBackgrounds) { widgetBackground ->
                Box(modifier = Modifier.padding(16.dp)) {
                    WidgetPreview(
                        background = widgetBackground,
                        soundByte = nachoSoundBytes.first(),
                        modifier = Modifier.clickable {
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
    background: WidgetBackground,
    soundByte: SoundByte,
    modifier: Modifier = Modifier,
    backgroundOnly: Boolean = false,
    useCompose: Boolean = USE_COMPOSE_WIDGET_PREVIEW
) {
    Box(modifier = modifier) {
        if (useCompose) {
            WidgetPreviewCompose(
                background = background,
                soundByte = soundByte,
                backgroundOnly = backgroundOnly
            )
        } else {
            WidgetPreviewAndroidView(
                background = background,
                soundByte = soundByte,
                backgroundOnly = backgroundOnly
            )
        }
    }
}

@Composable
fun WidgetPreviewAndroidView(
    background: WidgetBackground,
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
            soundByteBackgroundIV.setBackgroundResource(background.resourceId)
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
                    .setBackgroundResource(background.resourceId)
            }
        )
    }
}

@Composable
fun WidgetPreviewCompose(
    background: WidgetBackground,
    soundByte: SoundByte,
    modifier: Modifier = Modifier,
    backgroundOnly: Boolean = false
) {
    val elementBackgrounds = Color(0x6603DAC5)

    Box(
        modifier = modifier
            .width(144.dp)
            .height(144.dp),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = background.resourceId),
            contentDescription = "",
            modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        if (!backgroundOnly) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.wrapContentHeight(),
            ) {
                Text(
                    text = soundByte.name,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = elementBackgrounds),
                    fontSize = 24.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
                Row() {
                    listOf(
                        R.drawable.ic_baseline_play_arrow_24,
                        R.drawable.ic_baseline_settings_24
                    ).forEach {
                        Image(
                            painter = painterResource(id = it),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(elementBackgrounds)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewWidgetConfig() {
    NachoTheme {
        UpdateWidgetContent()
    }
}

@Preview
@Composable
fun PreviewWidgetPreviewInCompose() {
    NachoTheme() {
        WidgetPreviewCompose(
            background = nachoBackgrounds.first(),
            soundByte = nachoSoundBytes.first()
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