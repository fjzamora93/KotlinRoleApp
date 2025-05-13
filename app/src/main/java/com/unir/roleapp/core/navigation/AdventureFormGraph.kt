package com.unir.roleapp.core.navigation

import android.net.Uri

object AdventureFormGraph {
    private const val ARG = "adventureId"
    const val route   = "adventureForm"
    const val pattern = "$route?$ARG={$ARG}"

    fun createRoute(adventureId: String?): String =
        if (adventureId.isNullOrBlank()) route
        else "$route?$ARG=${Uri.encode(adventureId)}"
}