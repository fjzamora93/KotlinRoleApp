package com.unir.roleapp.adventure.domain.model

import com.google.firebase.firestore.DocumentId

data class Adventure(
    @DocumentId val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val historicalContext: String = "",
    val characters: List<AdventureCharacter> = emptyList(),
    val characterContexts: List<CharacterContext> = emptyList(),
    val acts: List<AdventureAct> = emptyList(),
){

    constructor() : this(
        id = "",
        title = "",
        description = "",
        userId = "",
        characters = emptyList(),
        acts = emptyList(),
        historicalContext = ""
    )

}