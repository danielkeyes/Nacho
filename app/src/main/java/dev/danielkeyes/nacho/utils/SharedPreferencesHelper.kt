package dev.danielkeyes.nacho.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dev.danielkeyes.nacho.composables.Background

private const val mSharedPrefFile = "dev.danielkeyes.nacho"
private const val BACKGROUND_KEY_SUFFIX = "background"
private const val SOUNDBYTE_KEY_SUFFIX = "soundbyte"

class SharedPreferencesHelper {
    // widgetid_backgroundname
    // widgetid_soundname
    //TODO something like this for set/get background and soundByte

    fun setBackground(widgetID: Int, background: String, context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)
        prefs.edit().putString(genKey(widgetID, BACKGROUND_KEY_SUFFIX), background).apply()
    }

    fun getBackground(widgetID: Int, context: Context, defaultValue: String): String {
        val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)
        return prefs.getString(genKey(widgetID, BACKGROUND_KEY_SUFFIX), defaultValue)?: defaultValue
    }

    private fun genKey(widgetID: Int, keySuffix: String): String {
       return "${widgetID}_${keySuffix}"
    }
}