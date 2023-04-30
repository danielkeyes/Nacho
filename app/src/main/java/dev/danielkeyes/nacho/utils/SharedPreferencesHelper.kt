package dev.danielkeyes.nacho.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dev.danielkeyes.nacho.resources.*

private const val mSharedPrefFile = "dev.danielkeyes.nacho"
private const val BACKGROUND_KEY_SUFFIX = "background"
private const val SOUNDBITE_KEY_SUFFIX = "soundbite"

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
                widgetBackgrounds.getBackground(backgroundName) ?: defaultValue
            } else {
                defaultValue
            }
        }

        fun setSoundbite(widgetID: Int, soundbite: Soundbite, context: Context) {
            val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)
            prefs.edit().putString(genKey(widgetID, SOUNDBITE_KEY_SUFFIX), soundbite.name).apply()
        }

        /**
         * get Soundbite for widgetId, if can't find a Soundbite for widgetID, use default
         */
        fun getSoundbite(widgetID: Int, context: Context, defaultValue: Soundbite): Soundbite {
            val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)
            val soundbiteName: String? = prefs.getString(genKey(widgetID, SOUNDBITE_KEY_SUFFIX), defaultValue.name)

            // if soundbiteName isn't null and can retrieve soundbite, return it, else, return
            // default value
            return if(soundbiteName != null) {
                soundbites.getSoundbite(soundbiteName) ?: defaultValue
            } else {
                defaultValue
            }
        }

        private fun genKey(widgetID: Int, keySuffix: String): String {
            return "${widgetID}_${keySuffix}"
        }
    }

}