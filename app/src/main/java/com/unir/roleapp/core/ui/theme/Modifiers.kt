package com.unir.roleapp.core.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.defaultRowModifier(): Modifier =
    this.fillMaxWidth()
        .padding(horizontal = 16.dp)
