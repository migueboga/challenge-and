package com.op.moviedb.domain.usecase

import com.op.moviedb.BuildConfig
import com.op.moviedb.data.repository.MovieDbDataRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPopularUseCase @Inject constructor(
    private val repository: MovieDbDataRepository
) {

    operator fun invoke() = repository.getPopular()
        .map {
            it.results.first()
        }
        .map {
            it.copy(profile_path = BuildConfig.MOVIE_DB_IMAGE_BASE_URL + it.profile_path)
        }
        .map { data ->
            data.copy(known_for = data.known_for.map {
                it.copy(poster_path = BuildConfig.MOVIE_DB_IMAGE_BASE_URL + it.poster_path)
            })
        }

}