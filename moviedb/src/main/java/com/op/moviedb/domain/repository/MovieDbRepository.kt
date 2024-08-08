package com.op.moviedb.domain.repository

import com.op.moviedb.domain.entity.PopularMovieResponse
import com.op.moviedb.domain.entity.PopularResponse
import kotlinx.coroutines.flow.Flow

interface MovieDbRepository {

    fun getPopular(): Flow<PopularResponse>

    fun getPopularMovies(): Flow<PopularMovieResponse>

    fun getTopRatedMovies(): Flow<PopularMovieResponse>

    fun getRecommendationsByMovieId(movieId: String): Flow<PopularMovieResponse>

}