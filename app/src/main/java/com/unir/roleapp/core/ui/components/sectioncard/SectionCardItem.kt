package com.unir.roleapp.core.ui.components.sectioncard

import kotlinx.serialization.Serializable

@Serializable
data class SectionCardItem(
    val title: String,
    val description: String,
    val imageResName: String,
    val route: String
)
