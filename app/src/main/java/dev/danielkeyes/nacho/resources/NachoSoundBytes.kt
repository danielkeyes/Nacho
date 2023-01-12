package dev.danielkeyes.nacho.resources

import dev.danielkeyes.nacho.R

data class SoundByte(val name: String, val resourceId: Int)

val nachoSoundBytes: List<SoundByte> =
    listOf(
        SoundByte("I wanna win", R.raw.nacho_libre_i_wanna_win),
        SoundByte("Anaconda Squeeze", R.raw.nacho_libre_anaconda_squeeze),
        SoundByte("Dang exciting", R.raw.nacho_libre_dang_exciting),
        SoundByte("Fantastic", R.raw.nacho_libre_fantastic),
        SoundByte("Fancy ladies", R.raw.nacho_libre_fancy_ladies),
        SoundByte("Floozy", R.raw.nacho_libre_floozy),
        SoundByte("Get that corn", R.raw.nacho_libre_get_that_corn),
        SoundByte("How did you find me", R.raw.nacho_libre_how_did_you_find_me),
        SoundByte("Go away", R.raw.nacho_libre_go_away),
        SoundByte("Listen to me", R.raw.nacho_libre_listen_to_me),
        SoundByte("My life it good", R.raw.nacho_libre_my_life_is_good),
        SoundByte("Punch to the face", R.raw.nacho_libre_punch_to_the_face),
        SoundByte("Salvation and stuff", R.raw.nacho_libre_salvation_and_stuff),
        SoundByte("Stretchy Pants", R.raw.nacho_libre_stretchy_pants),
        SoundByte("Take it easy", R.raw.nacho_libre_take_it_easy),
        SoundByte("Sucks to be me", R.raw.nacho_libre_sucks_to_be_me),
        SoundByte("Wrestle your neighbor", R.raw.nacho_libre_wrestle_your_neighbor),
        SoundByte("Really long fake name for testing", R.raw.nacho_libre_sucks_to_be_me),
    )

private val defaultSoundByte = nachoSoundBytes.first()

fun List<SoundByte>.getSoundByte(resourceId: Int): SoundByte {
    return this.find { it.resourceId == resourceId } ?: defaultSoundByte
}

fun List<SoundByte>.getSoundByte(name: String): SoundByte {
    return this.find { it.name.contains(name) } ?: defaultSoundByte
}
