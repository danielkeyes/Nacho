package dev.danielkeyes.nacho.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dev.danielkeyes.nacho.resources.*

private const val mSharedPrefFile = "dev.danielkeyes.nacho"
private const val BACKGROUND_KEY_SUFFIX = "background"
private const val SOUNDBYTE_KEY_SUFFIX = "soundbyte"
private const val WIDGET_STYLE_KEY_SUFFIX = "widgetStyle"

class SharedPreferencesHelper {

    companion object {
        fun setBackground(widgetID: Int, background: WidgetBackground, context: Context) {
            val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)
            prefs.edit().putString(genKey(widgetID, BACKGROUND_KEY_SUFFIX), background.name).apply()
        }

        /**
         * get WidgetBackground for widgetID, if can't find a WidgetBackground for widgetID, use default
         */
        fun getBackground(widgetID: Int, context: Context, defaultValue: WidgetBackground): WidgetBackground {
            val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)
            val backgroundName: String? = prefs.getString(genKey(widgetID, BACKGROUND_KEY_SUFFIX), defaultValue.name)

            // if name backgroundName isn't null and can retrieve background, return it, else, return default value
            return if(backgroundName != null) {
                nachoBackgrounds.getBackground(backgroundName) ?: defaultValue
            } else {
                defaultValue
            }
        }

        fun setSoundByte(widgetID: Int, soundByte: SoundByte, context: Context) {
            val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)
            prefs.edit().putString(genKey(widgetID, SOUNDBYTE_KEY_SUFFIX), soundByte.name).apply()
        }

        /**
         * get SoundByte for widgetId, if can't find a SoundByte for widgetID, use default
         */
        fun getSoundByte(widgetID: Int, context: Context, defaultValue: SoundByte): SoundByte {
            val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)
            val soundByteName: String? = prefs.getString(genKey(widgetID, SOUNDBYTE_KEY_SUFFIX), defaultValue.name)

            // if soundByteName isn't null and can retrieve soundByte, return it, else, return
            // default value
            return if(soundByteName != null) {
                nachoSoundBytes.getSoundByte(soundByteName) ?: defaultValue
            } else {
                defaultValue
            }
        }

        fun setWidgetStyle(widgetID: Int, style: WidgetStyle, context: Context) {
            val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)
            prefs.edit().putString(genKey(widgetID, WIDGET_STYLE_KEY_SUFFIX), style.name).apply()
        }

        /**
         * get WidgetStyle for widgetId, if can't find a WidgetStyle for widgetID, use default
         */
        fun getWidgetStyle(widgetID: Int, context: Context, defaultValue: WidgetStyle): WidgetStyle {
            val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)
            val widgetStyleName: String? = prefs.getString(genKey(widgetID, WIDGET_STYLE_KEY_SUFFIX), defaultValue.name)

            // if widgetStyleName isn't null and can retrieve widgetStyle, return it, else, return
            // default value
            return if(widgetStyleName != null) {
                nachoWidgetStyles.getWidgetStyle(widgetStyleName) ?: defaultValue
            } else {
                defaultValue
            }
        }

        private fun genKey(widgetID: Int, keySuffix: String): String {
            return "${widgetID}_${keySuffix}"
        }
    }

}