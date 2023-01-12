package dev.danielkeyes.nacho.utils

import android.util.Log
import dev.danielkeyes.nacho.R
import dev.danielkeyes.nacho.resources.SoundByte
import dev.danielkeyes.nacho.sanitizeSoundByteName
import java.lang.reflect.Field

class SoundByteUtils {

    companion object {

        // TODO test with weird prefix
        private const val PREFIX_ID = "nacho_libre_"

        fun getSoundBytes(prefixIdentifier: String = PREFIX_ID): List<SoundByte> {
            val soundBytes = mutableListOf<SoundByte>()
            getListRawAssets().filter { it.name.contains(prefixIdentifier) }.forEach { field ->
                soundBytes.add(
                    SoundByte(
                        field.name.sanitizeSoundByteName(prefixIdentifier),
                        field.getInt(field)
                    )
                )
            }
            return soundBytes.toList()
        }

        fun getSoundByte(keyword: String): SoundByte {
            return getSoundBytes().first { it.name.contains(keyword) }
        }

        private fun getListRawAssets(): Array<Field> {
            val fields: Array<Field> =
                R.raw::class.java.fields
            fields.forEach { field ->
                Log.e(
                    LOG_TAG, "name=${field.name} id=${field.getInt(field)}",
                )
            }
            return fields
        }
    }
}