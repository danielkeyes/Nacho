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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.danielkeyes.nacho.BuildConfig
import dev.danielkeyes.nacho.R
import dev.danielkeyes.nacho.ui.theme.NachoTheme

const val SCAFFOLD_ENABLED: Boolean = true

@Composable
fun MyScaffold(
    dropDownOptions: List<Pair<String, () -> Unit>>? = null,
    background: Int = R.drawable.nacholibretitleonlysmall2,
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
                                modifier = Modifier.wrapContentSize(Alignment.TopStart)
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
) {
    // https://developer.android
    // .com/reference/kotlin/androidx/compose/material/package-summary
    // #DropdownMenu(kotlin.Boolean,kotlin.Function0,androidx.compose.ui
    // .Modifier,androidx.compose.ui.unit.DpOffset,androidx.compose.ui.window
    // .PopupProperties,kotlin.Function1)
    var expandMenu by remember { mutableStateOf(false) }

    if (dropDownOptions != null && dropDownOptions.isNotEmpty()) {
        Box(
            modifier = modifier
        )
        {
            IconButton(onClick = { expandMenu = true }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "Menu"
                )
            }
            DropdownMenu(
                expanded = expandMenu,
                modifier = Modifier.background(color = Color.White),
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
                            color = Color.Black
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
fun ScaffoldTitleBar(background: Int) {
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
    NachoTheme() {
        MyScaffold(dropDownOptions = listOf(Pair("something", {}))) {
        }
    }
}