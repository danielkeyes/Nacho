package dev.danielkeyes.nacho.resources

import dev.danielkeyes.nacho.R

data class WidgetBackground(val name: String, val resourceId: Int) {
    override fun toString(): String {
        return "WidgetBackground(name='$name', resourceId=$resourceId)"
    }
}

val nachoBackgrounds2: List<WidgetBackground> =
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

val nachoBackgrounds: List<WidgetBackground> =
    listOf(
        WidgetBackground("Side Jumping", R.drawable.nacho_widget_background_sidejumping),
        WidgetBackground("Nacho Bathroom", R.drawable.nacho_widget_background_bathroom),
        WidgetBackground("Its the best", R.drawable.nacho_widget_background_itsthebest),
        WidgetBackground("Down Look", R.drawable.nacho_widget_background_downlook),
        WidgetBackground("Its the best 2", R.drawable.nacho_widget_background_itsthebest2),
        WidgetBackground("Sneaking", R.drawable.nacho_widget_background_sneaking),
        WidgetBackground("Flying", R.drawable.nacho_widget_background_flying),
        WidgetBackground("Movie Poster", R.drawable.nacho_widget_background_movieposter),
        WidgetBackground("Talking", R.drawable.nacho_widget_background_talking),
        WidgetBackground("Flying2", R.drawable.nacho_widget_background_flying2),
        WidgetBackground("Pointing", R.drawable.nacho_widget_background_pointing),
        WidgetBackground("Wrestle over the shoulder", R.drawable.nacho_widget_background_wrestleovertheshoulder),
        WidgetBackground("Flying3", R.drawable.nacho_widget_background_flying3),
        WidgetBackground("Scream pain", R.drawable.nacho_widget_background_screampain),
    )

/**
 * Retrieve WidgetBackground using [resourceID]
 */
fun List<WidgetBackground>.getBackground(resourceId: Int): WidgetBackground? {
    return this.find { it.resourceId == resourceId }
}

/**
 * Retrieve first WidgetBackground that partially matches [name]
 */
fun List<WidgetBackground>.getBackground(name: String): WidgetBackground? {
    return this.find { it.name.contains(name) }
}