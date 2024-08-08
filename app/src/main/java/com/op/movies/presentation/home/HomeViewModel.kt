package com.op.movies.presentation.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.op.moviedb.domain.usecase.GetPopularMovieUseCase
import com.op.moviedb.domain.usecase.GetRecommendationsByMovieIdUseCase
import com.op.moviedb.domain.usecase.GetTopRatedMovieUseCase
import com.op.movies.R
import com.op.movies.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovieUseCase: GetPopularMovieUseCase,
    private val getTopRatedMovieUseCase: GetTopRatedMovieUseCase,
    private val getRecommendationsByMovieIdUseCase: GetRecommendationsByMovieIdUseCase
) : BaseViewModel<HomeUiState>(HomeUiState()) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPopularMovies() {
        viewModelScope.launch {
            _uiState.postValue(_uiState.value?.copy(isLoading = true))
            getPopularMovieUseCase()
                .flatMapConcat { popularMovies ->
                    val movieId = popularMovies.first().id.toString()
                    getRecommendationsByMovieIdUseCase(movieId).map {
                        popularMovies to it
                    }
                }
                .catch {
                    Log.e("debug", it.localizedMessage, it)
                    _uiState.postValue(
                        _uiState.value?.copy(
                            isLoading = false, error = R.string.err_get_popular_movies
                        )
                    )
                }
                .collect { (popularMovies, recommendations) ->
                    _uiState.postValue(
                        _uiState.value?.copy(
                            isLoading = false,
                            popularMovieList = popularMovies.map {
                                MovieUiState(it.poster_path, it.title)
                            },
                            recommendationList = recommendations.map {
                                MovieUiState(it.poster_path, it.title)
                            }
                        )
                    )
                }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            _uiState.postValue(_uiState.value?.copy(isLoading = true))
            getTopRatedMovieUseCase()
                .catch {
                    Log.e("debug", it.localizedMessage, it)
                    _uiState.postValue(
                        _uiState.value?.copy(
                            isLoading = false, error = R.string.err_get_top_rated
                        )
                    )
                }
                .collect { response ->
                    _uiState.postValue(
                        _uiState.value?.copy(
                            isLoading = false,
                            topRatedMovieList = response.map {
                                MovieUiState(it.poster_path, it.title)
                            }
                        )
                    )
                }
        }
    }

}

