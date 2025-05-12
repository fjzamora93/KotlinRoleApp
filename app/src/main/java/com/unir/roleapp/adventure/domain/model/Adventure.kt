package com.unir.roleapp.adventure.domain.model

import com.google.firebase.firestore.DocumentId
import com.roleapp.character.data.model.local.CharacterEntity

data class Adventure(
    @DocumentId val id: String = "",
    val userId: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val title: String = "",
    val description: String = "",
    val historicalContext: String = "",
    val characters: List<CharacterEntity> = emptyList(),
    val characterContexts: List<CharacterContext> = emptyList(),
    val acts: List<AdventureAct> = emptyList(),
)