package com.example.googlemapdirection.usecase

import com.example.googlemapdirection.response.Meta
import com.example.googlemapdirection.utils.UiText

sealed class Resource<out T>(
    val data: T? = null,
    val message: UiText? = null,
    val meta: Meta? = null,
    val isSocketException: Boolean = false
) {
    class Success<T>(data: T? = null, message: UiText? = null, meta: Meta? = null) :
        Resource<T>(data, message, meta)

    class Error<T>(
        message: UiText?,
        data: T? = null,
        meta: Meta? = null,
        isSocketException: Boolean = false
    ) :
        Resource<T>(data, message, meta, isSocketException)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}