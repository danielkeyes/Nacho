package dev.danielkeyes.nacho.resources

import dev.danielkeyes.nacho.R

data class WidgetStyle(val name: String, val resourceId: Int) {
    override fun toString(): String {
        return "WidgetStyle(name='$name', resourceId=$resourceId)"
    }
}

val nachoWidgetStyles: List<WidgetStyle> =
    listOf(
        WidgetStyle("Widget with buttons", R.layout.nacho_soundbyte_widget_with_buttons),
        WidgetStyle("Widget with text only", R.layout.nacho_soundbyte_widget_text_only)
    )

private val defaultWidgetStyle = nachoWidgetStyles.first()

/**
 * Retrieve WidgetStyle that matches [resourceId]
 */
fun List<WidgetStyle>.getWidgetStyle(resourceId: Int): WidgetStyle {
    return this.find { it.resourceId == resourceId } ?: defaultWidgetStyle
}

/**
 * Retrieve first WidgetStyle that partially matches [name]
 */
fun List<WidgetStyle>.getWidgetStyle(name: String): WidgetStyle {
    return this.find { it.name.contains(name) } ?: defaultWidgetStyle
}
