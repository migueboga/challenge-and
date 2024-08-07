package com.op.moviedb.data.repository

import com.op.moviedb.MovieDbApiService
import com.op.moviedb.domain.entity.PopularMovieResponse
import com.op.moviedb.domain.entity.PopularResponse
import com.op.moviedb.domain.repository.MovieDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieDbDataRepository @Inject constructor(
    private val service: MovieDbApiService
): MovieDbRepository {

    override fun getPopular(): Flow<PopularResponse> = flow {
        val response = service.getPopular()
        emit(response)
    }.flowOn(Dispatchers.IO) // TODO: Inject

    override fun getPopularMovies(): Flow<PopularMovieResponse> = flow {
        val response = service.getPopularMovie()
        emit(response)
    }.flowOn(Dispatchers.IO) // TODO: Inject

    override fun getTopRatedMovies(): Flow<PopularMovieResponse> = flow {
        val response = service.getTopRatedMovie()
        emit(response)
    }.flowOn(Dispatchers.IO) // TODO: Inject

    override fun getRecommendationsByMovieId(movieId: String): Flow<PopularMovieResponse> = flow {
        val response = service.getRecommendationsByMovieId(movieId)
        emit(response)
    }.flowOn(Dispatchers.IO) // TODO: Inject


}