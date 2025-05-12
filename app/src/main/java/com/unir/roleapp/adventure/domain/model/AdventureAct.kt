package com.unir.roleapp.adventure.domain.model

import com.unir.roleapp.adventure.data.model.Scene

data class AdventureAct(
    val actNumber: Int,
    val title: String,
    val narrative: String,
    val mapDescription: String,

    val scenes: List<Scene?> // TODO: Borrar y arreglar conflictos
)