package com.op.storage.domain.repository

import com.op.storage.data.database.entity.PopularMovie
import kotlinx.coroutines.flow.Flow

interface PopularMovieRepository {

    fun getPopularMovies(): Flow<List<PopularMovie>>

    suspend fun insertAllPopularMovies(popularMovieList: List<PopularMovie>)

}