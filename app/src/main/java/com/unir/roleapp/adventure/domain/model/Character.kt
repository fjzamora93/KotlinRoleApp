package com.unir.roleapp.adventure.domain.model

import com.google.firebase.firestore.DocumentId

data class Character(
    val id: Long = 0,
    val name: String = "",
    val gameSessionId: String = "",
    val ap: Int = 0,
    val hp: Int = 0,
    val armor: Int = 0,
    val currentAp: Int = 0,
    val currentHp: Int = 0,
    val description: String = "",
    val imgUrl: String = "",
    val level: Int = 0,
    val race: String = "",
    val updatedAt: Long = 0,
    val userId: Int = 0
) {
    // Firestore necesita un constructor sin argumentos
    constructor() : this(
        id = 0,
        name = "",
        gameSessionId = "",
        ap = 0,
        hp = 0,
        armor = 0,
        currentAp = 0,
        currentHp = 0,
        description = "",
        imgUrl = "",
        level = 0,
        race = "",
        updatedAt = 0,
        userId = 0
    )
}