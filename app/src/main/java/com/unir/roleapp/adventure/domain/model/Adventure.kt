package com.unir.roleapp.adventure.domain.model

import com.google.firebase.firestore.DocumentId

data class Adventure(
    @DocumentId val id: String = "",
    val title: String = "",
    val description: String = "",
    val userId: String = "",
    val characters: List<Character> = emptyList(),
    val acts: List<AdventureAct> = emptyList(),
    val historicalContext: String = "",
    val characterContexts: List<CharacterContext> = emptyList()

    )
