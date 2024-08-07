package com.op.storage.data.repository

import com.op.storage.data.database.dao.PopularMovieDao
import com.op.storage.data.database.entity.PopularMovie
import com.op.storage.domain.repository.PopularMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class PopularMovieRoomRepository @Inject constructor(
    private val popularMovieDao: PopularMovieDao
): PopularMovieRepository {

    override fun getPopularMovies(): Flow<List<PopularMovie>> = popularMovieDao.get()

    override suspend fun insertAllPopularMovies(
        popularMovieList: List<PopularMovie>
    ) = popularMovieDao.insert(popularMovieList)
}