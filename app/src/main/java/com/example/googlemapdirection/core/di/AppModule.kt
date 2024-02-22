package com.example.googlemapdirection.core.di

import android.content.Context
import com.example.googlemapdirection.repo.GoogleCoordinatesRepositoryImpl
import com.example.googlemapdirection.network.Api
import com.example.googlemapdirection.repo.GoogleCoordinatesRepository
import com.example.googlemapdirection.usecase.GoogleCoordinatesUseCase
import com.example.googlemapdirection.utils.SessionManagerClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesGoogleCoordinatesRepository(
        api: Api,
    ): GoogleCoordinatesRepository {
        return GoogleCoordinatesRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun providesGoogleCoordinatesUseCase(
        googleCoordinatesRepository: GoogleCoordinatesRepository,
        @ApplicationContext appContext: Context,
    ): GoogleCoordinatesUseCase {
        return GoogleCoordinatesUseCase(
            googleCoordinatesRepository,
            provideSessionManagerClass(appContext)
        )
    }

    @Singleton
    @Provides
    fun provideSessionManagerClass(@ApplicationContext appContext: Context): SessionManagerClass =
        SessionManagerClass(appContext)
}