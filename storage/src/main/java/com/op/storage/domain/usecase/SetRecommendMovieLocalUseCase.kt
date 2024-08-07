package com.op.storage.domain.usecase

import com.op.storage.data.database.entity.RecommendMovie
import com.op.storage.data.repository.RecommendMovieRoomRepository
import javax.inject.Inject

class SetRecommendMovieLocalUseCase @Inject constructor(
    private val recommendMovieRepository: RecommendMovieRoomRepository
) {

    suspend operator fun invoke(
        recommendMovieList: List<RecommendMovie>
    ) = recommendMovieRepository.insertAllRecommendMovies(recommendMovieList)

}