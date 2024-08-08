package com.op.moviedb.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.op.moviedb.BuildConfig
import com.op.moviedb.MovieDbApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDbDependencyProvider {

    private const val CONNECTION_TIMEOUT = 10L
    private const val READ_TIMEOUT = 10L

    @Provides
    @IODispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        tokenInterceptor: TokenInterceptor
    ): Retrofit.Builder {
        val interceptors = mutableListOf<Interceptor>()
        interceptors.add(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        interceptors.add(tokenInterceptor)
        val client = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(tokenInterceptor)
        interceptors.forEach {
            client.addNetworkInterceptor(it)
        }
        val clientBuild = client.build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MOVIE_DB_BASE_URL)
            .client(clientBuild)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit.Builder): MovieDbApiService =
        retrofit.build().create(MovieDbApiService::class.java)

}