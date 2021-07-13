package com.practice.goodbadhabits.di

import com.practice.data.api.HabitApi
import com.practice.data.api.interceptors.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ApiKeyInterceptor())
            .addNetworkInterceptor(loggingInterceptor)
            .build()


    @Singleton
    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)


    @Provides
    fun provideBaseUrl():String = "https://droid-test-server.doubletapp.ru/"

}