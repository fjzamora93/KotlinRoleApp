package com.unir.roleapp.core.util

import android.content.Context
import java.util.Locale

object LocaleLanguageHelper {
    fun updateLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res    = context.resources
        val config = res.configuration
        config.setLocale(locale)
        // esto modifica los resources “en‑lugar”
        res.updateConfiguration(config, res.displayMetrics)
    }
}
