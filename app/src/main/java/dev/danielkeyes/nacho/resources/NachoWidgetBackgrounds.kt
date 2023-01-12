package dev.danielkeyes.nacho.resources

import dev.danielkeyes.nacho.R

data class WidgetBackground(val name: String, val resourceId: Int)

val nachoBackgrounds: List<WidgetBackground> =
    listOf(
        WidgetBackground("Flying Nacho", R.drawable.widget_background_nacho_flying_title),
        WidgetBackground("Nacho Head", R.drawable.widget_background_nacho_head),
        WidgetBackground("Nacho Monk", R.drawable.widget_background_nacho_monk),
        WidgetBackground("Nacho Movie Poster", R.drawable.widget_background_nacho_movie_poster),
        WidgetBackground("Nacho Libre Straight", R.drawable.widget_background_nacho_name_straight),
        WidgetBackground("Nacho Libre Tilted", R.drawable.widget_background_nacho_name_tilted),
        WidgetBackground("Nacho Pointing", R.drawable.widget_background_nacho_pointing),
        WidgetBackground("Nacho Wrestling", R.drawable.widget_background_nacho_ring),
        WidgetBackground("Stoic Nacho", R.drawable.widget_background_nacho_stoic),
    )

private val defaultBackground = nachoBackgrounds.first()

fun List<WidgetBackground>.getBackground(resourceId: Int): WidgetBackground {
    return this.find { it.resourceId == resourceId } ?: defaultBackground
}

fun List<WidgetBackground>.getBackground(name: String): WidgetBackground {
    return this.find { it.name.contains(name) } ?: defaultBackground
}