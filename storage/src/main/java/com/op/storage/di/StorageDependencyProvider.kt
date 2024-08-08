package com.op.storage.di

import android.content.Context
import androidx.room.Room
import com.op.storage.data.database.MovieDatabase
import com.op.storage.data.database.dao.PopularMovieDao
import com.op.storage.data.database.dao.ProfileDao
import com.op.storage.data.database.dao.RecommendMovieDao
import com.op.storage.data.database.dao.TopRatedMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageDependencyProvider {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        MovieDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providePopularMovieDao(
        database: MovieDatabase
    ): PopularMovieDao = database.popularMovieDao()

    @Singleton
    @Provides
    fun provideRecommendedMovieDao(
        database: MovieDatabase
    ): RecommendMovieDao = database.recommendMovieDao()

    @Singleton
    @Provides
    fun provideTopRatedMovieDao(
        database: MovieDatabase
    ): TopRatedMovieDao = database.topRatedMovieDao()

    @Singleton
    @Provides
    fun provideProfileDao(
        database: MovieDatabase
    ): ProfileDao = database.profileDao()

}