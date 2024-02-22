package com.example.googlemapdirection.core.di

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.example.googlemapdirection.App
import com.example.googlemapdirection.network.Api
import com.example.googlemapdirection.network.Api.Companion.SERVER_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        var versionName = ""
        try {
            val pInfo: PackageInfo = App.context!!.packageManager.getPackageInfo(App.context!!.packageName, 0)
            versionName = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .addInterceptor(Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("Authorization","5b3ce3597851110001cf6248012445a3631c4490b8eb49d5b0464cc2")
                    .header("App-Version", versionName)
                    .header("App-Platform", "android")
                return@Interceptor chain.proceed(builder.build())
            }).readTimeout(120, TimeUnit.SECONDS).connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun providesIntegrateApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

}