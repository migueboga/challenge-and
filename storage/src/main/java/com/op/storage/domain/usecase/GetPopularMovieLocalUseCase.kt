package com.op.storage.domain.usecase

import com.op.storage.data.repository.PopularMovieRoomRepository
import javax.inject.Inject

class GetPopularMovieLocalUseCase @Inject constructor(
    private val popularMovieRepository: PopularMovieRoomRepository
) {

    operator fun invoke() = popularMovieRepository.getPopularMovies()

}