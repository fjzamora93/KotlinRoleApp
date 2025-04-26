package com.unir.roleapp.character.data.model.remote

data class FirebaseCharacter (
    val id: Long,
    val userId: Int,
    val updatedAt: Long,

    val name: String,
    val description: String,
    val race: String,
    val armor: Int,

    val level: Int,
    val hp: Int,
    val currentHp: Int,
    val ap: Int,
    val currentAp: Int,

    val imgUrl: String,
    val gameSessionId: String,

)