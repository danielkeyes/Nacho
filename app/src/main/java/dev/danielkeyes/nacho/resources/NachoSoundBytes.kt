package dev.danielkeyes.nacho.resources

import dev.danielkeyes.nacho.R

data class SoundByte(val name: String, val resourceId: Int) {
    override fun toString(): String {
        return "SoundByte(name='$name', resourceId=$resourceId)"
    }
}

val nachoSoundBytes: List<SoundByte> =
    listOf(
        SoundByte("Nachoooo", R.raw.nacho_libre_nachoooo),
        SoundByte("Get that corn", R.raw.nacho_libre_get_that_corn),
        SoundByte("My life is good", R.raw.nacho_libre_my_life_is_good),
        SoundByte("Really good", R.raw.nacho_libre_really_good),
        SoundByte("Fantastic", R.raw.nacho_libre_fantastic),
        SoundByte("Take it easy", R.raw.nacho_libre_take_it_easy),
        SoundByte("Me no come on", R.raw.nacho_libre_me_no_come_on),
        SoundByte("Listen to me", R.raw.nacho_libre_listen_to_me),
        SoundByte("Anaconda Squeeze", R.raw.nacho_libre_anaconda_squeeze),
        SoundByte("Nipple twist", R.raw.nacho_libre_nipple_twist),
        SoundByte("I wanna win", R.raw.nacho_libre_i_wanna_win),
        SoundByte("Dang exciting", R.raw.nacho_libre_dang_exciting),
        SoundByte("Fancy ladies", R.raw.nacho_libre_fancy_ladies),
        SoundByte("Crazy Lady", R.raw.nacho_libre_crazy_lady),
        SoundByte("Floozy", R.raw.nacho_libre_floozy),
        SoundByte("How did you find me", R.raw.nacho_libre_how_did_you_find_me),
        SoundByte("Go away", R.raw.nacho_libre_go_away),
        SoundByte("Wrong idea", R.raw.nacho_libre_wrong_idea),
        SoundByte("Punch to the face", R.raw.nacho_libre_punch_to_the_face),
        SoundByte("Piledrive to the face", R.raw.nacho_libre_pile_drive_to_the_face),
        SoundByte("Salvation and stuff", R.raw.nacho_libre_salvation_and_stuff),
        SoundByte("Stretchy Pants", R.raw.nacho_libre_stretchy_pants),
        SoundByte("Let go my blouse", R.raw.nacho_libre_let_go_my_blouse),
        SoundByte("That one time", R.raw.nacho_libre_that_one_time),
        SoundByte("Wrestle your neighbor", R.raw.nacho_libre_wrestle_your_neighbor),
        SoundByte("Look like a fool", R.raw.nacho_libre_like_a_fool),
        SoundByte("It was stinky", R.raw.nacho_libre_it_was_stinky),
        SoundByte("Fun to wrestle", R.raw.nacho_libre_fun_to_wrestle),
        SoundByte("Sucks to be me", R.raw.nacho_libre_sucks_to_be_me),
        SoundByte("About the gospel", R.raw.nacho_libre_about_the_gospel),
        SoundByte("For some toast", R.raw.nacho_libre_for_some_toast),
    )

private val defaultSoundByte = nachoSoundBytes.first()

/**
 * Retrieve SoundByte using [resourceId]
 */
fun List<SoundByte>.getSoundByte(resourceId: Int): SoundByte {
    return this.find { it.resourceId == resourceId } ?: defaultSoundByte
}

/**
 * Retrieve first SoundByte that partially matches [name]
 */
fun List<SoundByte>.getSoundByte(name: String): SoundByte {
    return this.find { it.name.contains(name) } ?: defaultSoundByte
}
