package com.unir.roleapp.core.navigation.helpers

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.unir.roleapp.adventure.ui.viewmodels.AdventureFormViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun SharedAdventureForm(
    navController: NavHostController,
    content: @Composable (AdventureFormViewModel, String) -> Unit
) {
    val parentEntry = navController.getBackStackEntry(AdventureFormGraph.pattern)
    val formVm: AdventureFormViewModel = hiltViewModel(parentEntry)
    val advId = parentEntry.arguments
        ?.getString("adventureId")
        .orEmpty()

    content(formVm, advId)
}