package com.unir.roleapp.adventure.domain.model

data class Adventure(
    val id: String,               // Firestore doc ID = código de partida
    val title: String,
    val description: String,
    val createdAt: Long,
    val historicalContext: String? = null,
    val characterContexts: List<CharacterContext>? = null
)
