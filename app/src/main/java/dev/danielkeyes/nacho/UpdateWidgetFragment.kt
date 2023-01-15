package dev.danielkeyes.nacho

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import dev.danielkeyes.nacho.composables.MyScaffold
import dev.danielkeyes.nacho.resources.*
import dev.danielkeyes.nacho.ui.theme.NachoBlue
import dev.danielkeyes.nacho.ui.theme.NachoRed
import dev.danielkeyes.nacho.ui.theme.NachoTheme
import dev.danielkeyes.nacho.utils.nachoLog
import kotlinx.coroutines.launch


const val USE_ANDROID_VIEW = true
val WIDGET_HEIGHT = 115.dp
val WIDGET_WIDTH = 144.dp
val BACKGROUND_COLOR: Color = NachoBlue
val HEADER_BACKGROUND_COLOR: Color = NachoRed
val HEADER_TEXT_COLOR: Color = Color.White

class UpdateWidgetFragment : Fragment() {

    private val widgetViewModel: WidgetViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        return ComposeView(requireContext()).apply {
            setContent {
                NachoTheme() {
                    Surface() {
                        val widgets by widgetViewModel.widgets.observeAsState()
                        val isLoading: Boolean by widgetViewModel.isLoading.observeAsState(true)

                        UpdateWidgetContent(
                            widgets = widgets,
                            isLoading = isLoading,
                            playSoundByte = { soundByte ->
                                widgetViewModel.playSoundByte(soundByte)
                            },
                            updateWidgetBackground = { widgetID: Int, background:
                            WidgetBackground ->
                                widgetViewModel.updateWidgetBackground(widgetID, background)
                            },
                            updateWidgetSoundByte = { widgetID: Int, soundByte: SoundByte ->
                                widgetViewModel.updateWidgetSoundByte(widgetID, soundByte)
                            },
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        widgetViewModel.refreshWidgets()
    }

    override fun onPause() {
        super.onPause()
        widgetViewModel.updateWidgets()
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun UpdateWidgetContent(
    widgets: List<NachoWidget>?,
    isLoading: Boolean,
    updateWidgetBackground: (Int, WidgetBackground) -> Unit,
    updateWidgetSoundByte: (Int, SoundByte) -> Unit,
    playSoundByte: (SoundByte) -> Unit,
) {
    MyScaffold() {
        if (isLoading) {
            FullScreenMessage(text = "Retrieving widgets...")
        } else {
            // If no widgets, display message telling user to add widgets
            if (widgets == null || widgets.isEmpty()) {
                FullScreenMessage(text = "No widgets found... Go add one!")
            } else {
                var currentWidgetId by rememberSaveable { mutableStateOf(widgets.first().widgetId) }

                val pagerState = rememberPagerState()
                val pages = listOf("Background", "SoundByte")

                val coroutineScope = rememberCoroutineScope()

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(modifier = Modifier
                        .background(HEADER_BACKGROUND_COLOR)
                        .padding(12.dp)) {
                        Text(
                            text = "Widget Preview",
                            color = HEADER_TEXT_COLOR,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.button.copy(
                                textAlign = TextAlign.Center
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }

//                val styledText: @Composable (() -> Unit)? = text?.let {
//                    @Composable {
//                        val style = MaterialTheme.typography.button.copy(textAlign = TextAlign
//                        .Center)
//                        ProvideTextStyle(style, content = text)
//                    }
//                }

                    LazyColumnWithSelection(widgets, { widgetId -> currentWidgetId = widgetId })
                    Column(Modifier.weight(1f)) {
                        TabRow(
                            // Our selected tab is our current page
                            selectedTabIndex = pagerState.currentPage,
                            // Override the indicator, using the provided pagerTabIndicatorOffset
                            // modifier
                            indicator = { tabPositions ->
                                TabRowDefaults.Indicator(
                                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                                )
                            },
                            backgroundColor = HEADER_BACKGROUND_COLOR,
                            contentColor = HEADER_TEXT_COLOR
                        ) {
                            pages.forEachIndexed { index, title ->
                                Tab(
                                    text = { Text(title) },
                                    selected = pagerState.currentPage == index,
                                    onClick = {
                                        coroutineScope.launch {
                                            pagerState.scrollToPage(index)
                                        }
                                    },
                                )
                            }
                        }

                        HorizontalPager(
                            count = pages.size,
                            state = pagerState,
                        ) { page ->
                            Column() {
                                if (pages[page] == "Background") {
                                    BackgroundPicker { background: WidgetBackground ->
                                        updateWidgetBackground(currentWidgetId, background)
                                    }
                                } else {
                                    SoundBytePicker(updateSoundByte = { soundByte: SoundByte ->
                                        updateWidgetSoundByte(currentWidgetId, soundByte)
                                    }, playSound = { soundByte: SoundByte ->
                                        playSoundByte(soundByte)
                                    })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FullScreenMessage(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BACKGROUND_COLOR),
        contentAlignment = Alignment.Center) {
        Text(
            text = text,
            color = HEADER_BACKGROUND_COLOR,
            fontSize = 32.sp,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.button.copy(
                textAlign = TextAlign.Center
            )
        )
    }
}
@Composable
fun LazyColumnWithSelection(
    widgets: List<NachoWidget>,
    widgetSelected: (Int) -> Unit,
) {
    var selectedIndex by remember { mutableStateOf(0) }
    val onItemClick = { index: Int ->
        selectedIndex = index
        nachoLog(index.toString())
        widgetSelected(widgets[index].widgetId)
    }

    val listState = rememberLazyListState()
    // Remember a CoroutineScope to be able to launch
    val coroutineScope = rememberCoroutineScope()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BACKGROUND_COLOR)
            .wrapContentHeight()
            .padding(8.dp), state = listState
    ) {
        itemsIndexed(widgets) { index, widget ->
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(4.dp)
            ) {
                val previewModifier = if (index == selectedIndex) {
                    Modifier.background(Color.White, shape = RoundedCornerShape(8.dp))
                } else {
                    Modifier
                }

                WidgetPreview(widget, modifier = previewModifier
                    .padding(4.dp)
                    .clickable {
                        onItemClick(index)
                        coroutineScope.launch {
                            listState.animateScrollToItem(index)
                        }
                    })
            }
        }
    }
}

@Composable
fun BackgroundPicker(updateBackground: (WidgetBackground) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(color = BACKGROUND_COLOR),
        content = {
            items(items = nachoBackgrounds) { widgetBackground ->
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    WidgetPreview(
                        NachoWidget(1, widgetBackground, nachoSoundBytes.first()),
                        modifier = Modifier.clickable {
                            updateBackground(widgetBackground)
                        },
                        backgroundOnly = true
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SoundBytePicker(
    updateSoundByte: (SoundByte) -> Unit, playSound: (SoundByte) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(color = BACKGROUND_COLOR),
        content = {
            items(items = nachoSoundBytes) { soundByte ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .alpha(.8f), onClick = {
                            updateSoundByte(soundByte)
                        }, border = BorderStroke(
                            width = 4.dp, color = MaterialTheme.colors.primaryVariant
                        )
                    ) {
                        Row (
                          verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painter = painterResource(
                                id = R.drawable.ic_baseline_play_arrow_24
                            ),
                                contentDescription = "play ${soundByte.name}",
                                modifier = Modifier
                                    .clickable {
                                        playSound(soundByte)
                                    }
                                    .padding(8.dp))
                            Text(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f),
                                text = soundByte.name.capitalize(), // I get it, but I want to use this for the time being
                                fontSize = 16.sp,
                                textAlign = TextAlign.Start,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun WidgetPreview(
    widget: NachoWidget,
    modifier: Modifier = Modifier,
    backgroundOnly: Boolean = false,
    useAndroidView: Boolean = USE_ANDROID_VIEW
) {
    Box(modifier = modifier) {
        if (useAndroidView) {
            WidgetPreviewAndroidView(
                widget = widget, backgroundOnly = backgroundOnly
            )

        } else {
            WidgetPreviewCompose(
                widget = widget, backgroundOnly = backgroundOnly
            )
        }
    }
}

@Composable
fun WidgetPreviewAndroidView(
    widget: NachoWidget, modifier: Modifier = Modifier, backgroundOnly: Boolean = false
) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(10.dp))
    ) {
        AndroidView(factory = { context ->
            val view = LayoutInflater.from(context)
                .inflate(R.layout.nacho_soundbyte_widget, null, false)
            val soundByteNameTV = view.findViewById<TextView>(R.id.soundbyte_name_tv)
            val soundByteBackgroundIV = view.findViewById<ImageView>(R.id.widget_background_iv)
            val nameAndButtonLL = view.findViewById<LinearLayout>(R.id.name_and_buttons_ll)

            view.findViewById<ImageView>(R.id.play_ib).isClickable = false
            view.findViewById<ImageView>(R.id.settings_ib).isClickable = false

            soundByteNameTV.text = widget.soundByte.name
            soundByteBackgroundIV.setBackgroundResource(widget.background.resourceId)
            if (backgroundOnly) {
                nameAndButtonLL.visibility = View.GONE
            }

            view
        }, modifier = Modifier
            .width(WIDGET_WIDTH)
            .height(WIDGET_HEIGHT), update = { view ->
            view.findViewById<TextView>(R.id.soundbyte_name_tv).text = widget.soundByte.name
            view.findViewById<ImageView>(R.id.widget_background_iv)
                .setImageResource(widget.background.resourceId)
        })
    }
}

@Composable
fun WidgetPreviewCompose(
    widget: NachoWidget, modifier: Modifier = Modifier, backgroundOnly: Boolean = false
) {
    Box(
        modifier = modifier
            .width(WIDGET_WIDTH)
            .height(WIDGET_HEIGHT),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = widget.background.resourceId),
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
                    text = widget.soundByte.name,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
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
                        R.drawable.ic_baseline_play_arrow_24, R.drawable.ic_baseline_settings_24
                    ).forEach {
                        Image(
                            painter = painterResource(id = it),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clip(RoundedCornerShape(10.dp))
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
fun PreviewUpdateWidgetContent() {
    NachoTheme() {
        UpdateWidgetContent(
            widgets = listOf(
                NachoWidget(1, background = nachoBackgrounds[1], soundByte = nachoSoundBytes[1]),
                NachoWidget(2, background = nachoBackgrounds[4], soundByte = nachoSoundBytes[2]),
                NachoWidget(3, background = nachoBackgrounds[8], soundByte = nachoSoundBytes[3]),
            ),
            isLoading = false, {a,b-> },{a,b ->},{}
        )
    }
}

@Preview
@Composable
fun PreviewLazyColumnWithSelection() {
    NachoTheme() {
        LazyColumnWithSelection(widgets = listOf(
            NachoWidget(1, background = nachoBackgrounds[1], soundByte = nachoSoundBytes[1]),
            NachoWidget(2, background = nachoBackgrounds[4], soundByte = nachoSoundBytes[2]),
            NachoWidget(3, background = nachoBackgrounds[8], soundByte = nachoSoundBytes[3]),
        ), {})
    }
}

@Preview
@Composable
fun PreviewWidgetPreviewInCompose() {
    NachoTheme() {
        WidgetPreviewCompose(
            fakeWidget
        )
    }
}

@Preview()
@Composable
fun PreviewWidgetPreview() {
    NachoTheme {
        WidgetPreview(fakeWidget)
    }
}

@Preview
@Composable
fun PreviewBackgroundPicker() {
    NachoTheme {
        BackgroundPicker {}
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSoundBytePicker() {
    NachoTheme {
        SoundBytePicker({}, {})
    }
}

private val fakeWidget: NachoWidget =
    NachoWidget(1, nachoBackgrounds.first(), nachoSoundBytes.first())