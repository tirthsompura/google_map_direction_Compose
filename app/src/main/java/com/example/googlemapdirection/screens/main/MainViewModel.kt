package com.example.googlemapdirection.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.googlemapdirection.core.AppPreferences
import com.example.googlemapdirection.ui.theme.AppTheme
import com.example.googlemapdirection.utils.SessionManagerClass
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val sessionManagerClass: SessionManagerClass,
) : ViewModel() {

    var stateApp by mutableStateOf(MainState())

}

sealed class MainEvent {
    data class ThemeChange(val theme: AppTheme) : MainEvent()
}

data class MainState(
    val theme: AppTheme = AppPreferences.getTheme(),
)