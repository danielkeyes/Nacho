package dev.danielkeyes.nacho.resources

import dev.danielkeyes.nacho.R

data class WidgetBackground(val name: String, val resourceId: Int) {
    override fun toString(): String {
        return "WidgetBackground(name='$name', resourceId=$resourceId)"
    }
}

val widgetBackgrounds: List<WidgetBackground> =
    listOf(
        WidgetBackground("Side Jumping", R.drawable.nacho_widget_background_sidejumping),
        WidgetBackground("Movie Poster", R.drawable.nacho_widget_background_movieposter),
        WidgetBackground("Stoic", R.drawable.nacho_widget_background_stoic),
        WidgetBackground("Sneaking", R.drawable.nacho_widget_background_sneaking),
        WidgetBackground("Flying ring", R.drawable.nacho_widget_background_flying_ring),
        WidgetBackground("Flying3", R.drawable.nacho_widget_background_flying3),
        WidgetBackground("Scream pain", R.drawable.nacho_widget_background_screampain),
        WidgetBackground("Its the best", R.drawable.nacho_widget_background_itsthebest),
        WidgetBackground("Its the best 2", R.drawable.nacho_widget_background_itsthebest2),
        WidgetBackground("Its the best 3", R.drawable.nacho_widget_background_itsthebest3),
        WidgetBackground("Flying2", R.drawable.nacho_widget_background_flying2),
        WidgetBackground("Nacho Bathroom", R.drawable.nacho_widget_background_bathroom),
        WidgetBackground("Talking", R.drawable.nacho_widget_background_talking),
        WidgetBackground("Pointing", R.drawable.nacho_widget_background_pointing),
        WidgetBackground("Head", R.drawable.nacho_widget_background_head),
        WidgetBackground("Name", R.drawable.nacho_widget_background_name),
        WidgetBackground("Tilted name", R.drawable.nacho_widget_background_nametilted),
        WidgetBackground("Pair", R.drawable.nacho_widget_background_pair),
        WidgetBackground("Down Look", R.drawable.nacho_widget_background_downlook),
        WidgetBackground("Flying", R.drawable.nacho_widget_background_flying),
        WidgetBackground("Wrestle over the shoulder", R.drawable.nacho_widget_background_wrestleovertheshoulder),

        )

val defaultWidgetBackground = widgetBackgrounds.first()

/**
 * Retrieve WidgetBackground using [resourceID]
 */
fun List<WidgetBackground>.getBackground(resourceId: Int): WidgetBackground {
    return this.find { it.resourceId == resourceId } ?: defaultWidgetBackground
}

/**
 * Retrieve first WidgetBackground that partially matches [name]
 */
fun List<WidgetBackground>.getBackground(name: String): WidgetBackground {
    return this.find { it.name.contains(name) } ?: defaultWidgetBackground

}