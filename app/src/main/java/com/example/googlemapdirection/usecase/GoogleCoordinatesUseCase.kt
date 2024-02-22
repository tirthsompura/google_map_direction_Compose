package com.example.googlemapdirection.usecase

import com.example.googlemapdirection.repo.GoogleCoordinatesRepository
import com.example.googlemapdirection.response.CoordinatesRequest
import com.example.googlemapdirection.response.CoordinatesResponse
import com.example.googlemapdirection.utils.SessionManagerClass
import com.example.googlemapdirection.utils.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GoogleCoordinatesUseCase @Inject constructor(
    private val repository: GoogleCoordinatesRepository, val sessionManagerClass: SessionManagerClass
) : BaseUseCase<CoordinatesRequest, Flow<Resource<CoordinatesResponse>>> {
    override suspend fun execute(request: CoordinatesRequest): Flow<Resource<CoordinatesResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = repository.coordinatesApi(request)
                emit(onResponseHandleCore(resp = response, sessionManagerClass = sessionManagerClass))
            } catch (e: Exception) {
                emit(Resource.Error(UiText.DynamicString( "Something went wrong!!")))
                e.printStackTrace()
            }
        }
    }
}
