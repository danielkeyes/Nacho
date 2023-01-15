package dev.danielkeyes.nacho.utils

import android.util.Log

const val LOG_TAG = "Nacho"

fun nachoLog(msg: String){
    Log.e(LOG_TAG, msg)
}

fun logNullCheck(any: Any?, name: String) {
    if(any == null) {
        nachoLog("$name is null")
    } else {
        nachoLog("$name is not null")
    }
}

fun Array<String?>?.logAll(name: String) {
    if (this == null || this.isNotEmpty()){
        nachoLog( "$name is null or empty")
    } else {
        nachoLog("$name contains")
        this.forEach {
            nachoLog("$it")
        }
    }
}