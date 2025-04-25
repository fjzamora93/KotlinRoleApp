package com.unir.roleapp.adventure.domain.usecase

data class CreateAdventureRequest(
    val title: String,
    val description: String,
    val genre: String = ""
)