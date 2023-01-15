package dev.danielkeyes.nacho.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.danielkeyes.nacho.BuildConfig
import dev.danielkeyes.nacho.R
import dev.danielkeyes.nacho.ui.theme.NachoTheme

const val SCAFFOLD_ENABLED: Boolean = true

@Composable
fun MyScaffold(navController: NavController, content: @Composable (PaddingValues) -> Unit) {
    var expandMenu by remember { mutableStateOf(false) }

    if (SCAFFOLD_ENABLED) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(MaterialTheme.colors.primary)
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
//                                .height(24.dp),
                            painter = painterResource(id = R.drawable.nacholibretitleonlysmall),
                            contentDescription = "",
                            contentScale = ContentScale.FillHeight,
                            alignment = Alignment.Center
                        )
                    }
                    // https://developer.android
                    // .com/reference/kotlin/androidx/compose/material/package-summary
                    // #DropdownMenu(kotlin.Boolean,kotlin.Function0,androidx.compose.ui
                    // .Modifier,androidx.compose.ui.unit.DpOffset,androidx.compose.ui.window
                    // .PopupProperties,kotlin.Function1)
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()) {
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier.wrapContentSize(Alignment.TopStart)
                        ) {
                            IconButton(onClick = { expandMenu = true }) {
                                Icon(
                                    imageVector = Icons.Rounded.MoreVert,
                                    contentDescription = "Menu"
                                )
                            }
                            DropdownMenu(expanded = expandMenu,
                                onDismissRequest = { expandMenu = false }) {
                                DropdownMenuItem(onClick = {
                                    // navigate to UpdateWidgetFragment
                                    navController.navigate(R.id.updateWidgetFragment)
                                }) {
                                    Text("Configure Widget")
                                }
                                if (BuildConfig.DEBUG) {
                                    // TODO future add any debug only items I want here for testing
                                }
                            }
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

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewMyScaffold() {
    NachoTheme() {
        MyScaffold(navController = rememberNavController()) {

        }
    }
}