package com.practice.goodbadhabits.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder()
            .header("Authorization", apiKey)
            .build()



        return chain.proceed(request)
    }

    companion object {
        private const val apiKey = "2e6383da-6372-4f37-96f4-a7c0e39b1c6b"
    }
}
//"2e6383da-6372-4f37-96f4-a7c0e39b1c6b"
//"2e6383da-6372-4f37-96f4-a7c0e39b1c6b"
//"2e6383da-6372-4f37-96f4-a7c0e39b1c6b"
