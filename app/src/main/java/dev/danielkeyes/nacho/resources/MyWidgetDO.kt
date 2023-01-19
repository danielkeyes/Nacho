package dev.danielkeyes.nacho.resources

data class MyWidgetDO(
    val widgetId: Int,
    var background: WidgetBackground,
    var soundByte: SoundByte
) {
    override fun toString(): String {
        return "MyWidgetDO(widgetId=$widgetId, background=$background, soundByte=$soundByte)"
    }
}