package com.op.storage.data.repository

import com.op.storage.data.database.dao.RecommendMovieDao
import com.op.storage.data.database.entity.RecommendMovie
import com.op.storage.domain.repository.RecommendMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecommendMovieRoomRepository @Inject constructor(
    private val recommendMovieDao: RecommendMovieDao
): RecommendMovieRepository {

    override fun getRecommendMovies(): Flow<List<RecommendMovie>> = recommendMovieDao.get()

    override suspend fun insertAllRecommendMovies(
        recommendMovieList: List<RecommendMovie>
    ) = recommendMovieDao.insert(recommendMovieList)
}