package com.op.moviedb.di

import com.op.moviedb.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class TokenInterceptor @Inject constructor(): Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        requestBuilder.header("Authorization", "Bearer ${BuildConfig.MOVIE_DB_API_KEY}")
        val response = chain.proceed(requestBuilder.build())
        return response
    }
}