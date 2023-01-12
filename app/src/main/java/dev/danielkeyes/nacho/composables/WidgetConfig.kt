package dev.danielkeyes.nacho.composables

import android.view.LayoutInflater
import android.view.View
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
import androidx.compose.runtime.Composable
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
import dev.danielkeyes.nacho.R
import dev.danielkeyes.nacho.resources.SoundByte
import dev.danielkeyes.nacho.resources.WidgetBackground
import dev.danielkeyes.nacho.resources.nachoBackgrounds
import dev.danielkeyes.nacho.resources.nachoSoundBytes
import dev.danielkeyes.nacho.ui.theme.NachoTheme

// TODO: Move to resource package ? Backgrounds
// add ability to get specific one by name, if null return first?
// can store name in shared preferences



// TODO: Move to resource package ? SoundByte
// add ability to get specific one by name, if null return first?
// can store name in shared preferences


