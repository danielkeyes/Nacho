package dev.danielkeyes.nacho

import android.appwidget.AppWidgetManager
import android.content.ComponentName
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
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import dev.danielkeyes.nacho.resources.SoundByte
import dev.danielkeyes.nacho.resources.WidgetBackground
import dev.danielkeyes.nacho.resources.nachoBackgrounds
import dev.danielkeyes.nacho.resources.nachoSoundBytes
import dev.danielkeyes.nacho.ui.theme.ElementBackgrounds
import kotlinx.coroutines.launch

class UpdateWidgetFragment : Fragment() {

    private val widgetViewModel: WidgetViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        return ComposeView(requireContext()).apply {
            setContent {

                NachoTheme() {
                    val widgets by widgetViewModel.widgets.observeAsState()
                    val isLoading by widgetViewModel.isLoading.observeAsState(true)

                    UpdateWidgetContent(
                        widgets = widgets,
                        isLoading = isLoading,
                        refreshWidgets = { widgetViewModel.refreshWidgets() },
                        updateWidgetBackground = {widgetID: Int, background: WidgetBackground ->
                            widgetViewModel.updateWidgetBackground(widgetID, background)
                        },
                        updateWidgetSoundByte = {widgetID: Int, soundByte: SoundByte ->
                            widgetViewModel.updateWidgetSoundByte(widgetID, soundByte)
                        },
                        updateWidgets = { widgetId: Int, background: WidgetBackground, soundByte: SoundByte ->
                            widgetViewModel.updateWidget(widgetId, background, soundByte ) },
                        getWidgets = { widgetViewModel.getWidgets() } )
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

const val USE_COMPOSE_WIDGET_PREVIEW = true

@OptIn(ExperimentalPagerApi::class)
@Composable
fun UpdateWidgetContent(
    widgets: List<NachoWidget>?,
    isLoading: Boolean,
    updateWidgetBackground: (Int, WidgetBackground) -> Unit,
    updateWidgetSoundByte: (Int, SoundByte) -> Unit,
    refreshWidgets: () -> Unit,
    updateWidgets: (Int, WidgetBackground, SoundByte) -> Unit,
    getWidgets: () -> List<NachoWidget>?,

) {
    if (isLoading) {
        Text(
            text = "Loading",
            color = Color.White
        ) // why is my surface and text black
    } else {
        // If no widgets, display message telling user to add widgets
        if (widgets == null || widgets.isEmpty()) {
            Text(
                text = "You must add a widget first!",
                color = Color.White
            ) // why is my surface and text black
        } else {
            val backgroundName by rememberSaveable { mutableStateOf(nachoBackgrounds.first().name) }
            val soundByteName by rememberSaveable { mutableStateOf(nachoSoundBytes.first().name) }
            var currentWidgetId by rememberSaveable { mutableStateOf(widgets.first().widgetId) }

            val pagerState = rememberPagerState()
            val pages = listOf("Background", "SoundByte")

            val coroutineScope = rememberCoroutineScope()

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.background(color = Color.Cyan),
                    text = "${widgets.first().widgetId} ${widgets.first().background.name} " +
                            "${widgets.first().soundByte.name}"
                )
                Text(text = "Widget Preview")
                LazyColumnWithSelection(widgets, { widgetId -> currentWidgetId = widgetId })
                Column(Modifier.weight(1f)) {
                    TabRow(
                        // Our selected tab is our current page
                        selectedTabIndex = pagerState.currentPage,
                        // Override the indicator, using the provided pagerTabIndicatorOffset modifier
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                            )
                        },
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
                                SoundBytePicker { soundByte: SoundByte ->
                                    updateWidgetSoundByte(currentWidgetId, soundByte)
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
fun LazyColumnWithSelection(
    widgets: List<NachoWidget>,
    widgetSelected: (Int) -> Unit,
) {
    var selectedIndex by remember { mutableStateOf(0) }
    val onItemClick = {
            index: Int -> selectedIndex = index
            widgetSelected(widgets[index].widgetId)
    }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
//        widgets.forEach {
//            item {
//                WidgetPreview(it, modifier = Modifier
//                    .clickable { onItemClick }
//                    .padding(8.dp))
//            }
//        }
        items(widgets) { widget ->
            WidgetPreview(widget,
                modifier = Modifier
                    .clickable { onItemClick }
                    .padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewLazyColumnWithSelection() {
    NachoTheme() {
        LazyColumnWithSelection(
            widgets = listOf(
                NachoWidget(1, background = nachoBackgrounds[1], soundByte = nachoSoundBytes[1]),
                NachoWidget(2, background = nachoBackgrounds[2], soundByte = nachoSoundBytes[2]),
                NachoWidget(3, background = nachoBackgrounds[3], soundByte = nachoSoundBytes[3]),
            ),
            {}
        )
    }
}

@Composable
fun BackgroundPicker(updateBackground: (WidgetBackground) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(items = nachoBackgrounds) { widgetBackground ->
                Box(modifier = Modifier.padding(16.dp)) {
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

@Composable
fun SoundBytePicker(updateSoundByte: (SoundByte) -> Unit) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        nachoSoundBytes.forEach { soundByte ->
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .alpha(.8f),
                onClick = {
                    updateSoundByte(soundByte)
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
    widget: NachoWidget,
    modifier: Modifier = Modifier,
    backgroundOnly: Boolean = false,
    useCompose: Boolean = USE_COMPOSE_WIDGET_PREVIEW
) {
    Box(modifier = modifier) {
        if (useCompose) {
            WidgetPreviewCompose(
                widget = widget,
                backgroundOnly = backgroundOnly
            )
        } else {
            WidgetPreviewAndroidView(
                widget = widget,
                backgroundOnly = backgroundOnly
            )
        }
    }
}

@Composable
fun WidgetPreviewAndroidView(
    widget: NachoWidget,
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

            soundByteNameTV.text = widget.soundByte.name
            soundByteBackgroundIV.setBackgroundResource(widget.background.resourceId)
            if (backgroundOnly) {
                nameAndButtonLL.visibility = View.GONE
            }

            view
        },
            modifier = Modifier
                .width(144.dp)
                .height(144.dp),
            update = { view ->
                view.findViewById<TextView>(R.id.soundbyte_name_tv).text = widget.soundByte.name
                view.findViewById<ImageView>(R.id.widget_background_iv)
                    .setBackgroundResource(widget.background.resourceId)
            }
        )
    }
}

@Composable
fun WidgetPreviewCompose(
    widget: NachoWidget,
    modifier: Modifier = Modifier,
    backgroundOnly: Boolean = false
) {
    Box(
        modifier = modifier
            .width(144.dp)
            .height(144.dp),
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
                        .wrapContentHeight()
                        .background(color = ElementBackgrounds),
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
                                .background(ElementBackgrounds)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewWidgetConfig() {
//    NachoTheme {
//        UpdateWidgetContent(
//            listOf(fakeWidget),
//            false,
//            {1, wid},
//            {},
//            {},
//            {},
//            {},
//        )
//    }
//}

@Preview
@Composable
fun PreviewWidgetPreviewInCompose() {
    NachoTheme() {
        WidgetPreviewCompose(
            fakeWidget
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

@Preview
@Composable
fun PreviewSoundBytePicker() {
    NachoTheme {
        SoundBytePicker {}
    }
}

private val fakeWidget: NachoWidget = NachoWidget(1, nachoBackgrounds.first(), nachoSoundBytes.first())