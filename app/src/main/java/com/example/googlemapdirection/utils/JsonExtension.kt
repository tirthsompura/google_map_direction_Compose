package com.example.googlemapdirection.utils

import com.google.gson.Gson

/**
 * Transform simple object to String with Gson
 */
inline fun <reified T : Any> T.toPrettyJson() : String = Gson().toJson(this, T::class.java)

/**
 * Transform String Json to Object
 */
inline fun <reified T : Any> String.fromPrettyJson() : T = Gson().fromJson(this , T::class.java)
