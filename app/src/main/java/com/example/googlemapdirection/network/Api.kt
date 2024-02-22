package com.example.googlemapdirection.network

import com.example.googlemapdirection.response.CoordinatesRequest
import com.example.googlemapdirection.response.CoordinatesResponse
import com.example.googlemapdirection.utils.API_CONST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface Api {
    companion object {
        const val SERVER_URL = "https://api.openrouteservice.org/v2/directions/"
    }

    @POST(API_CONST.GOOGLE_MAP)
    suspend fun coordinatesApi(@Body request: CoordinatesRequest): Response<CoordinatesResponse>

}