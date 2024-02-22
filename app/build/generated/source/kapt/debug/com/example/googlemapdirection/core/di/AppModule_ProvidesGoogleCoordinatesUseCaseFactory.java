// Generated by Dagger (https://dagger.dev).
package com.example.googlemapdirection.core.di;

import android.content.Context;
import com.example.googlemapdirection.repo.GoogleCoordinatesRepository;
import com.example.googlemapdirection.usecase.GoogleCoordinatesUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AppModule_ProvidesGoogleCoordinatesUseCaseFactory implements Factory<GoogleCoordinatesUseCase> {
  private final Provider<GoogleCoordinatesRepository> googleCoordinatesRepositoryProvider;

  private final Provider<Context> appContextProvider;

  public AppModule_ProvidesGoogleCoordinatesUseCaseFactory(
      Provider<GoogleCoordinatesRepository> googleCoordinatesRepositoryProvider,
      Provider<Context> appContextProvider) {
    this.googleCoordinatesRepositoryProvider = googleCoordinatesRepositoryProvider;
    this.appContextProvider = appContextProvider;
  }

  @Override
  public GoogleCoordinatesUseCase get() {
    return providesGoogleCoordinatesUseCase(googleCoordinatesRepositoryProvider.get(), appContextProvider.get());
  }

  public static AppModule_ProvidesGoogleCoordinatesUseCaseFactory create(
      Provider<GoogleCoordinatesRepository> googleCoordinatesRepositoryProvider,
      Provider<Context> appContextProvider) {
    return new AppModule_ProvidesGoogleCoordinatesUseCaseFactory(googleCoordinatesRepositoryProvider, appContextProvider);
  }

  public static GoogleCoordinatesUseCase providesGoogleCoordinatesUseCase(
      GoogleCoordinatesRepository googleCoordinatesRepository, Context appContext) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providesGoogleCoordinatesUseCase(googleCoordinatesRepository, appContext));
  }
}