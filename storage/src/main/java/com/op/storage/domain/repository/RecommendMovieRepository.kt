package com.op.storage.domain.repository

import com.op.storage.data.database.entity.PopularMovie
import com.op.storage.data.database.entity.RecommendMovie
import kotlinx.coroutines.flow.Flow

interface RecommendMovieRepository {

    fun getRecommendMovies(): Flow<List<RecommendMovie>>

    suspend fun insertAllRecommendMovies(recommendMovieList: List<RecommendMovie>)

}