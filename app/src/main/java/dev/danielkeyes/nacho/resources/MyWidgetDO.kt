package dev.danielkeyes.nacho.resources

data class MyWidgetDO(
    val widgetId: Int,
    var background: WidgetBackground,
    var soundbite: Soundbite
) {
    override fun toString(): String {
        return "MyWidgetDO(widgetId=$widgetId, background=$background, soundbite=$soundbite)"
    }
}