package com.unir.sheet.ui.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import com.unir.sheet.di.LocalNavigationViewModel

// BOTONES Y COMPONENTES DON FUNCIONES PREDEFINIDAS INCORPORADAS Y QUE ESTÁN EN VARIAS PARTES DE LA APP

@Composable
fun BackButton() {
    val navigationViewModel = LocalNavigationViewModel.current

    NavigationButton(
        text = "",
        icon = Icons.Default.ArrowBackIosNew,
        onClick = {
            navigationViewModel.goBack()
        })
}



