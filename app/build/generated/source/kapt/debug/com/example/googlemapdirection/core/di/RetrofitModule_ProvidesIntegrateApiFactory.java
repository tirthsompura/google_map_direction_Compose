// Generated by Dagger (https://dagger.dev).
package com.example.googlemapdirection.core.di;

import com.example.googlemapdirection.network.Api;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class RetrofitModule_ProvidesIntegrateApiFactory implements Factory<Api> {
  private final Provider<Retrofit> retrofitProvider;

  public RetrofitModule_ProvidesIntegrateApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public Api get() {
    return providesIntegrateApi(retrofitProvider.get());
  }

  public static RetrofitModule_ProvidesIntegrateApiFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new RetrofitModule_ProvidesIntegrateApiFactory(retrofitProvider);
  }

  public static Api providesIntegrateApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(RetrofitModule.INSTANCE.providesIntegrateApi(retrofit));
  }
}