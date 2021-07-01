package com.practice.data.api

import com.practice.data.api.interceptors.ApiKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {
    companion object {
        private val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        private val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ApiKeyInterceptor())
            .addNetworkInterceptor(loggingInterceptor)
            .build()
    }

    private fun getRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()

    fun createHabitApi(baseUrl: String): HabitApi =
        getRetrofit(baseUrl).create(HabitApi::class.java)

}