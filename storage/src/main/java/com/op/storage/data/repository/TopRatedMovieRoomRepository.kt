package com.op.storage.data.repository

import com.op.storage.data.database.dao.TopRatedMovieDao
import com.op.storage.data.database.entity.TopRatedMovie
import com.op.storage.domain.repository.TopRatedMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopRatedMovieRoomRepository @Inject constructor(
    private val topRatedMovieDao: TopRatedMovieDao
): TopRatedMovieRepository {

    override fun getTopRatedMovies(): Flow<List<TopRatedMovie>> = topRatedMovieDao.get()

    override suspend fun insertAllTopRatedMovies(
        topRatedMovieList: List<TopRatedMovie>
    ) = topRatedMovieDao.insert(topRatedMovieList)
}