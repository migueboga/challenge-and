package com.op.storage.domain.usecase

import com.op.storage.data.repository.TopRatedMovieRoomRepository
import javax.inject.Inject

class GetTopRatedMovieLocalUseCase @Inject constructor(
    private val topRatedMovieRepository: TopRatedMovieRoomRepository
) {

    operator fun invoke() = topRatedMovieRepository.getTopRatedMovies()

}