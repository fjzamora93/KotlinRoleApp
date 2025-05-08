package com.unir.roleapp.adventure.domain.model

import com.unir.roleapp.adventure.data.model.Scene

data class AdventureAct(
    val actNumber: Int,
    val scenes: List<Scene>  // reutilizamos tu data class Scene
)