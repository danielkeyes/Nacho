package dev.danielkeyes.nacho.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

private const val mSharedPrefFile = "dev.danielkeyes.nacho"
private const val SOME_KEY = "SomeKey"
private const val SOME_KEY2 = "SomeKey2"

class SharedPreferencesHelper {
    fun setSomeKey(dateInMilliSeconds: Long, context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)

        prefs.edit().putLong(SOME_KEY, dateInMilliSeconds).apply()

    }

    fun getSomeKey(context: Context): Long {
        val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)

        return prefs.getLong(SOME_KEY, System.currentTimeMillis())
    }

    fun setSomeKey2(babyName: String, context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)

        prefs.edit().putString(SOME_KEY2, babyName).apply()
    }

    fun getSomeKey2(context: Context): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)

        return prefs.getString(SOME_KEY2, null)
    }

}