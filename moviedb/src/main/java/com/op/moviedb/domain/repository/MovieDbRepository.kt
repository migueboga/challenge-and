package com.op.moviedb.domain.repository

import com.op.moviedb.domain.entity.PopularResponse
import kotlinx.coroutines.flow.Flow

interface MovieDbRepository {

    fun getPopular(): Flow<PopularResponse>

}