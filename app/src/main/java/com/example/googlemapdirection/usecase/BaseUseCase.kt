package com.example.googlemapdirection.usecase

import com.example.googlemapdirection.App
import com.example.googlemapdirection.utils.RootGraph
import com.example.googlemapdirection.utils.SessionManagerClass
import com.example.googlemapdirection.utils.UiText
import org.json.JSONObject
import retrofit2.Response

interface BaseUseCase<In, Out> {
    suspend fun execute(request: In): Out

    fun <T> onResponseHandleCore(
        resp: Response<T>,
        isStoreUserDetail: Boolean = false,
        sessionManagerClass: SessionManagerClass? = null
    ): Resource<T> {
        when {
            resp.isSuccessful -> {
                val response = resp.body()
               return Resource.Success(
                    response,
                    meta = null
                )
            }
            resp.code() == 401 -> {
                return try {
                    navigateToLoginPage()
                    Resource.Error(UiText.DynamicString("Unauthorized"))
                } catch (e: Exception) {
                    navigateToLoginPage()
                    e.printStackTrace()
                    Resource.Error(UiText.DynamicString("Unauthorized"))
                }
            }

            else -> {
                return try {
                    val res = resp.errorBody()
                    val objectError = JSONObject(res!!.string())
                    val message = objectError.getString("message")
                    Resource.Error(UiText.DynamicString(message))
                } catch (e: Exception) {
                    Resource.Error(UiText.DynamicString("Something went wrong"))
                }
            }
        }
    }
}

fun navigateToLoginPage() {
    val navController = App.navHostController
    navController?.navigate(RootGraph.ROOT) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
}