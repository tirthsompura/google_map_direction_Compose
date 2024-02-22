package com.example.googlemapdirection.usecase

import com.example.googlemapdirection.response.Meta
import com.example.googlemapdirection.utils.UiText

data class StateHandler<T>(
    var isLoading: Boolean = false,
    val isLoadMore: Boolean = false,
    var data: T? = null,
    var message: UiText? = null,
    var error: UiText? = null,
    val meta : Meta? = null,
    var request: Any? = null,
    var isRefreshing: Any? = null
)
