package dev.danielkeyes.nacho.utils

import android.util.Log

const val LOG_TAG = "Nacho"

fun myLog(msg: String){
    Log.e(LOG_TAG, msg)
}

fun logNullCheck(any: Any?, name: String) {
    if(any == null) {
        myLog("$name is null")
    } else {
        myLog("$name is not null")
    }
}

fun Array<String?>?.logAll(name: String) {
    if (this == null || this.isNotEmpty()){
        myLog( "$name is null or empty")
    } else {
        myLog("$name contains")
        this.forEach {
            myLog("$it")
        }
    }
}