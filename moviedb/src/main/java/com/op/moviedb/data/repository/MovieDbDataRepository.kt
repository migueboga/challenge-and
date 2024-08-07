package com.op.moviedb.data.repository

import com.op.moviedb.MovieDbApiService
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


}