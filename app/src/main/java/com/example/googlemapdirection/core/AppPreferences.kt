package com.example.googlemapdirection.core

import com.example.googlemapdirection.ui.theme.AppTheme


object AppPreferences {
    //key
    const val APP_LANG = "AppLang"

    const val APP_THEME = "AppTheme"

    fun getTheme(): AppTheme {
        return AppTheme.Default
    }
}