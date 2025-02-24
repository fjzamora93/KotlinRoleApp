package com.unir.sheet.data.remote.model

data class ApiCharacterRequest(
    val id: Int,
    val name: String,
    val description: String,
    val race: String,
    val gender: String,
    val size: Int,
    val age: Int,
    val gold: Int,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val imgUrl: String?,
    val gameSessionId: Int?,
    val userId: Int?,
    val roleClass: String,
)
