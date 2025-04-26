package com.unir.roleapp.character.ui.screens.characterSheet.helper.util

fun calculateInitative(dexterityBonus: Int) : Int{
    var randomValue = (4..8).random()
    when (dexterityBonus) {
        in 0..5 -> randomValue += 3
        in 6..7 -> randomValue += 2
        in 8..10 -> randomValue += 1
        in 11..12 -> randomValue += 0
        in 13..15 -> randomValue -= 1
        in 15..16 -> randomValue -= 2
        in 17..20 -> randomValue -= 3
        else -> 0
    }


    return randomValue


}