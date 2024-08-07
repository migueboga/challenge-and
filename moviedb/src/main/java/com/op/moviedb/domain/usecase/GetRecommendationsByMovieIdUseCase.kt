package com.op.moviedb.domain.usecase

import com.op.moviedb.BuildConfig
import com.op.moviedb.data.repository.MovieDbDataRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecommendationsByMovieIdUseCase @Inject constructor(
    private val repository: MovieDbDataRepository
) {

    operator fun invoke(movieId: String) = repository.getRecommendationsByMovieId(movieId)
        .map {
            it.results
        }
        .map { results ->
            results.map {
                it.copy(poster_path = BuildConfig.MOVIE_DB_IMAGE_BASE_URL + it.poster_path)
            }
        }

}