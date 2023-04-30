package dev.danielkeyes.nacho.resources

import dev.danielkeyes.nacho.R


data class Soundbite(val name: String, val resourceId: Int) {
    override fun toString(): String {
        return "Soundbite(name='$name', resourceId=$resourceId)"
    }
}

val soundbites: List<Soundbite> =
    listOf(
        Soundbite("Get that corn", R.raw.nacho_libre_get_that_corn),
        Soundbite("Nachoooo", R.raw.nacho_libre_nachoooo),
        Soundbite("Fantastic", R.raw.nacho_libre_fantastic),
        Soundbite("My life is good", R.raw.nacho_libre_my_life_is_good),
        Soundbite("Really good", R.raw.nacho_libre_really_good),
        Soundbite("Take it easy", R.raw.nacho_libre_take_it_easy),
        Soundbite("Me no come on", R.raw.nacho_libre_me_no_come_on),
        Soundbite("Listen to me", R.raw.nacho_libre_listen_to_me),
        Soundbite("Anaconda Squeeze", R.raw.nacho_libre_anaconda_squeeze),
        Soundbite("Nipple twist", R.raw.nacho_libre_nipple_twist),
        Soundbite("I wanna win", R.raw.nacho_libre_i_wanna_win),
        Soundbite("Dang exciting", R.raw.nacho_libre_dang_exciting),
        Soundbite("Fancy ladies", R.raw.nacho_libre_fancy_ladies),
        Soundbite("Crazy Lady", R.raw.nacho_libre_crazy_lady),
        Soundbite("Floozy", R.raw.nacho_libre_floozy),
        Soundbite("How did you find me", R.raw.nacho_libre_how_did_you_find_me),
        Soundbite("Go away", R.raw.nacho_libre_go_away),
        Soundbite("Wrong idea", R.raw.nacho_libre_wrong_idea),
        Soundbite("Punch to the face", R.raw.nacho_libre_punch_to_the_face),
        Soundbite("Piledrive to the face", R.raw.nacho_libre_pile_drive_to_the_face),
        Soundbite("Salvation and stuff", R.raw.nacho_libre_salvation_and_stuff),
        Soundbite("Stretchy Pants", R.raw.nacho_libre_stretchy_pants),
        Soundbite("Let go my blouse", R.raw.nacho_libre_let_go_my_blouse),
        Soundbite("That one time", R.raw.nacho_libre_that_one_time),
        Soundbite("Wrestle your neighbor", R.raw.nacho_libre_wrestle_your_neighbor),
        Soundbite("Look like a fool", R.raw.nacho_libre_like_a_fool),
        Soundbite("It was stinky", R.raw.nacho_libre_it_was_stinky),
        Soundbite("Fun to wrestle", R.raw.nacho_libre_fun_to_wrestle),
        Soundbite("Sucks to be me", R.raw.nacho_libre_sucks_to_be_me),
        Soundbite("About the gospel", R.raw.nacho_libre_about_the_gospel),
        Soundbite("For some toast", R.raw.nacho_libre_for_some_toast),
    )

val defaultSoundbite = soundbites.first()

/**
 * Retrieve Soundbite using [resourceId]
 */
fun List<Soundbite>.getSoundbite(resourceId: Int): Soundbite {
    return this.find { it.resourceId == resourceId } ?: defaultSoundbite
}

/**
 * Retrieve first Soundbite that partially matches [name]
 */
fun List<Soundbite>.getSoundbite(name: String): Soundbite {
    return this.find { it.name.contains(name) } ?: defaultSoundbite
}
