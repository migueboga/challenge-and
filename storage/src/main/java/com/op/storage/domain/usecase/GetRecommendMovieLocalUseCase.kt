package com.op.storage.domain.usecase

import com.op.storage.data.repository.RecommendMovieRoomRepository
import javax.inject.Inject

class GetRecommendMovieLocalUseCase @Inject constructor(
    private val recommendMovieRepository: RecommendMovieRoomRepository
) {

    operator fun invoke() = recommendMovieRepository.getRecommendMovies()

}