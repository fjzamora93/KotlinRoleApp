package com.unir.roleapp.adventure.domain.model

import com.unir.roleapp.adventure.data.model.Scene

data class AdventureAct(
    val actNumber: Int,
    val title: String,
    val narrative: String,
    val mapDescription: String,
    val mapURL: String,
    val scenes: List<Scene?> // TODO: Borrar y arreglar conflictos
){

    constructor() : this(
        actNumber =0,
        title = "",
        narrative ="",
        mapDescription = "",
        mapURL = "",
        scenes = emptyList()

    )

}