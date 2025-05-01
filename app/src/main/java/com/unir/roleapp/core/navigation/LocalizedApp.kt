package com.unir.roleapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.unir.roleapp.core.di.LocalLanguageSetter
import com.unir.roleapp.core.util.LocaleLanguageHelper

@Composable
fun LocalizedApp(
    language: String,
    content: @Composable () -> Unit
) {
    val ctx = LocalContext.current

    LocaleLanguageHelper.updateLocale(ctx, language)
    content()
}
