package dev.danielkeyes.nacho.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.danielkeyes.nacho.BuildConfig
import dev.danielkeyes.nacho.R
import dev.danielkeyes.nacho.ui.theme.SoundBoardTheme

const val SCAFFOLD_BACKGROUND = R.drawable.nacho_libre_title
const val SCAFFOLD_ENABLED: Boolean = true

@Composable
fun MyScaffold(
    dropDownOptions: List<Pair<String, () -> Unit>>? = null,
    background: Int = SCAFFOLD_BACKGROUND,
    dropDownOptionsIconColor: Color = Color.White,
    dropDownOptionsBackgroundColor: Color = Color.White,
    dropDownOptionsOptionsColor: Color = Color.Black,    
    content: @Composable (PaddingValues) -> Unit
) {
    if (SCAFFOLD_ENABLED) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(MaterialTheme.colors.primary)
                ) {
                    ScaffoldTitleBar(background = background)
                    if (dropDownOptions != null && dropDownOptions.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            DropDownOptions(
                                dropDownOptions,
                                modifier = Modifier.wrapContentSize(Alignment.TopStart),
                                dropDownOptionsIconColor,
                                dropDownOptionsBackgroundColor,
                                dropDownOptionsOptionsColor,
                            )
                        }
                    }
                }
            }, content = content
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            content
        }
    }
}

@Composable
fun DropDownOptions(
    dropDownOptions: List<Pair<String, () -> Unit>>?,
    modifier: Modifier = Modifier,
    iconColor: Color = Color.White,
    backgroundColor: Color = Color.White,
    optionsColor: Color = Color.Black,
) {
    var expandMenu by remember { mutableStateOf(false) }

    if (dropDownOptions != null && dropDownOptions.isNotEmpty()) {
        Box(
            modifier = modifier
        )
        {
            IconButton(onClick = { expandMenu = true }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "Menu",
                    tint = iconColor,
                )
            }
            DropdownMenu(
                expanded = expandMenu,
                modifier = Modifier.background(color = backgroundColor),
                onDismissRequest = { expandMenu = false }) {
                dropDownOptions.forEach {
                    DropdownMenuItem(
                        onClick = {
                            it.second()
                            expandMenu = false
                        }
                    ) {
                        Text(
                            it.first,
                            color = optionsColor
                        )
                    }
                }
                if (BuildConfig.DEBUG) {
                    // TODO future add any debug only items I want here for testing
                }
            }
        }
    }
}

@Composable
fun ScaffoldTitleBar(
    background: Int
) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(4.dp),
            painter = painterResource(id = background),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            alignment = Alignment.Center
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewMyScaffold() {
    SoundBoardTheme() {
        MyScaffold(dropDownOptions = listOf(Pair("something", {}))) {
        }
    }
}