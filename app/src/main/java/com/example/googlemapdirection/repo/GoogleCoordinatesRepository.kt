package com.example.googlemapdirection.repo

import com.example.googlemapdirection.response.CoordinatesRequest
import com.example.googlemapdirection.response.CoordinatesResponse
import retrofit2.Response

interface GoogleCoordinatesRepository {
    suspend fun coordinatesApi(request: CoordinatesRequest): Response<CoordinatesResponse>
}
