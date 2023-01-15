package dev.danielkeyes.nacho.resources

data class NachoWidget(
    val widgetId: Int,
    var background: WidgetBackground,
    var soundByte: SoundByte
) {
    override fun toString(): String {
        return "NachoWidget(widgetId=$widgetId, background=$background, soundByte=$soundByte)"
    }
}