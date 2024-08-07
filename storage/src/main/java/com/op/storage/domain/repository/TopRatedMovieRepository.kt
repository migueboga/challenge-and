package com.op.storage.domain.repository

import com.op.storage.data.database.entity.PopularMovie
import com.op.storage.data.database.entity.TopRatedMovie
import kotlinx.coroutines.flow.Flow

interface TopRatedMovieRepository {

    fun getTopRatedMovies(): Flow<List<TopRatedMovie>>

    suspend fun insertAllTopRatedMovies(topRatedMovieList: List<TopRatedMovie>)

}