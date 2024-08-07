package com.op.storage.domain.usecase

import com.op.storage.data.database.entity.PopularMovie
import com.op.storage.data.repository.PopularMovieRoomRepository
import javax.inject.Inject

class SetPopularMovieLocalUseCase @Inject constructor(
    private val popularMovieRepository: PopularMovieRoomRepository
) {

    suspend operator fun invoke(
        popularMovieList: List<PopularMovie>
    ) = popularMovieRepository.insertAllPopularMovies(popularMovieList)

}