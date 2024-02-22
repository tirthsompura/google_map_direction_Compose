package com.example.googlemapdirection.repo

import com.example.googlemapdirection.network.Api
import com.example.googlemapdirection.response.CoordinatesRequest
import com.example.googlemapdirection.response.CoordinatesResponse
import retrofit2.Response
import javax.inject.Inject

class GoogleCoordinatesRepositoryImpl @Inject constructor(
    private val api: Api
) : GoogleCoordinatesRepository {

    override suspend fun coordinatesApi(request: CoordinatesRequest): Response<CoordinatesResponse> {
        return api.coordinatesApi(request)
    }
}