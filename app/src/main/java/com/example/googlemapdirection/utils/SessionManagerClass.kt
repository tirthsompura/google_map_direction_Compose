package com.example.googlemapdirection.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import com.example.googlemapdirection.response.UserResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManagerClass @Inject constructor(@ApplicationContext context: Context) {

    var showNoInternetDialog = mutableStateOf(false)

    private var prefs: SharedPreferences? = null

    init {
        prefs =
            context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
    }

    var loginUserData: UserResponse?
        get() {
            return if (prefs!!.getString(PrefKey.Login_user_response, "")?.isNotEmpty() == true) {
                val userResponse: UserResponse? =
                    prefs!!.getString(PrefKey.Login_user_response, "")?.fromPrettyJson()
                userResponse
            } else
                null
        }
        set(value) {
            prefs!!.edit().putString(PrefKey.Login_user_response, value?.toPrettyJson()).apply()
        }

    fun clearSessionData() {
        prefs?.edit()?.clear()?.apply()
    }
}

object PrefKey {
    const val Login_user_response = "Login_user_response"
}