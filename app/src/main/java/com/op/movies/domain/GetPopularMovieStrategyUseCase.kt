package com.op.movies.domain

import android.util.Log
import com.op.moviedb.domain.usecase.GetPopularMovieUseCase
import com.op.movies.data.strategy.DataAccessStrategy
import com.op.storage.data.database.entity.PopularMovie
import com.op.storage.domain.usecase.GetPopularMovieLocalUseCase
import com.op.storage.domain.usecase.SetPopularMovieLocalUseCase
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPopularMovieStrategyUseCase @Inject constructor(
    private val getPopularMovieUseCase: GetPopularMovieUseCase,
    private val getPopularMovieLocalUseCase: GetPopularMovieLocalUseCase,
    private val setPopularMovieLocalUseCase: SetPopularMovieLocalUseCase
) {

    operator fun invoke() = DataAccessStrategy.performOperation(
            databaseCall = { getPopularMovieLocalUseCase() },
            networkCall = {
                getPopularMovieUseCase()
                    .map {
                        it.map { PopularMovie(id = it.id.toLong(),path = it.poster_path, title = it.title) }
                    }
            },
            saveResult = { result ->
                Log.d("debug", "RESULT: $result")
                setPopularMovieLocalUseCase(result)
            },
        )


}