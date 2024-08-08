package com.op.storage.domain.usecase

import com.op.storage.data.database.entity.TopRatedMovie
import com.op.storage.data.repository.TopRatedMovieRoomRepository
import javax.inject.Inject

class SetTopRatedMovieLocalUseCase @Inject constructor(
    private val topRatedMovieRepository: TopRatedMovieRoomRepository
) {

    suspend operator fun invoke(
        topRatedMovieList: List<TopRatedMovie>
    ) = topRatedMovieRepository.insertAllTopRatedMovies(topRatedMovieList)

}