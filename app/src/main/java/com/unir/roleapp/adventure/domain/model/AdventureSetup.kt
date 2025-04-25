package com.unir.roleapp.adventure.domain.model

data class AdventureSetup(
    val title: String,
    val description: String,
    val context: String,
    val code: String,
    val characterIds: List<String>
)