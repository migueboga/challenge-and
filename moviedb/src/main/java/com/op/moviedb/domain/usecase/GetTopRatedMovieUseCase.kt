package com.op.moviedb.domain.usecase

import com.op.moviedb.BuildConfig
import com.op.moviedb.data.repository.MovieDbDataRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTopRatedMovieUseCase @Inject constructor(
    private val repository: MovieDbDataRepository
) {

    operator fun invoke() = repository.getTopRatedMovies()
        .map {
            it.results
        }
        .map { results ->
            results.map {
                it.copy(poster_path = BuildConfig.MOVIE_DB_IMAGE_BASE_URL + it.poster_path)
            }
        }

}