package com.op.movies.presentation.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.op.moviedb.domain.entity.Movie
import com.op.moviedb.domain.usecase.GetPopularMovieUseCase
import com.op.moviedb.domain.usecase.GetRecommendationsByMovieIdUseCase
import com.op.moviedb.domain.usecase.GetTopRatedMovieUseCase
import com.op.movies.R
import com.op.movies.data.strategy.DataAccessStrategy
import com.op.movies.domain.GetPopularMovieStrategyUseCase
import com.op.movies.presentation.base.BaseViewModel
import com.op.storage.data.database.entity.PopularMovie
import com.op.storage.data.database.entity.RecommendMovie
import com.op.storage.data.database.entity.TopRatedMovie
import com.op.storage.domain.usecase.GetPopularMovieLocalUseCase
import com.op.storage.domain.usecase.GetRecommendMovieLocalUseCase
import com.op.storage.domain.usecase.GetTopRatedMovieLocalUseCase
import com.op.storage.domain.usecase.SetPopularMovieLocalUseCase
import com.op.storage.domain.usecase.SetRecommendMovieLocalUseCase
import com.op.storage.domain.usecase.SetTopRatedMovieLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovieUseCase: GetPopularMovieUseCase,
    private val getTopRatedMovieUseCase: GetTopRatedMovieUseCase,
    private val getRecommendationsByMovieIdUseCase: GetRecommendationsByMovieIdUseCase,
    private val setPopularMovieLocalUseCase: SetPopularMovieLocalUseCase,
    private val setRecommendMovieLocalUseCase: SetRecommendMovieLocalUseCase,
    private val getPopularMovieLocalUseCase: GetPopularMovieLocalUseCase,
    private val getRecommendMovieLocalUseCase: GetRecommendMovieLocalUseCase,
    private val getTopRatedLocalUseCase: GetTopRatedMovieLocalUseCase,
    private val setTopRatedMovieLocalUseCase: SetTopRatedMovieLocalUseCase
) : BaseViewModel<HomeUiState>(HomeUiState()) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTopRatedMovies() {
        viewModelScope.launch {
            _uiState.postValue(_uiState.value?.copy(isLoading = true))
            getTopRatedLocalUseCase()
                .distinctUntilChanged()
                .flatMapConcat { localTopRated ->
                    _uiState.postValue(
                        _uiState.value?.copy(
                            isLoading = false,
                            topRatedMovieList = localTopRated.map {
                                MovieUiState(it.path, it.title)
                            }
                        )
                    )
                    getTopRatedMovieUseCase()
                }
                .catch {
                    Log.e("debug", it.localizedMessage, it)
                    _uiState.postValue(
                        _uiState.value?.copy(
                            isLoading = false, error = R.string.err_get_top_rated
                        )
                    )
                }
                .collect { response ->
                    saveLocalTopRatedMovies(response)
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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPopularMovies() {
        viewModelScope.launch {
            _uiState.postValue(_uiState.value?.copy(isLoading = true))
            getPopularMovieLocalUseCase()
                .distinctUntilChanged()
                .flatMapConcat { local ->
                    _uiState.postValue(
                        _uiState.value?.copy(
                            popularMovieList = local.map {
                                MovieUiState(it.path, it.title)
                            }
                        )
                    )
                    getPopularMovieUseCase()
                }
                .flatMapConcat { popularMovies ->
                    saveLocalPopularMovies(popularMovies)
                    _uiState.postValue(
                        _uiState.value?.copy(
                            popularMovieList = popularMovies.map {
                                MovieUiState(it.poster_path, it.title)
                            }
                        )
                    )
                    getRecommendMovieLocalUseCase().map {
                        it to popularMovies.first().id.toString()
                    }
                }
                .distinctUntilChanged()
                .flatMapConcat { (recommendedLocal, movieId) ->
                    _uiState.postValue(
                        _uiState.value?.copy(
                            recommendationList = recommendedLocal.map {
                                MovieUiState(it.path, it.title)
                            }
                        )
                    )
                    getRecommendationsByMovieIdUseCase(movieId)
                }
                .catch {
                    Log.e("debug", it.localizedMessage, it)
                    _uiState.postValue(
                        _uiState.value?.copy(
                            isLoading = false, error = R.string.err_get_popular_movies
                        )
                    )
                }
                .collect { remoteRecommendations ->
                    saveLocalRecommendationMovies(remoteRecommendations)
                    _uiState.postValue(
                        _uiState.value?.copy(
                            recommendationList = remoteRecommendations.map {
                                MovieUiState(it.poster_path, it.title)
                            }
                        )
                    )
                }
        }
    }

    override fun clearError() {
        _uiState.postValue(
            _uiState.value?.copy(error = null)
        )
    }

    private fun saveLocalPopularMovies(popularMovieList: List<Movie>) {
        viewModelScope.launch(Dispatchers.IO) {
            setPopularMovieLocalUseCase(popularMovieList.map {
                PopularMovie(it.id.toLong(), it.poster_path, it.title)
            })
        }
    }

    private fun saveLocalRecommendationMovies(recommendationMovieList: List<Movie>) {
        viewModelScope.launch(Dispatchers.IO) {
            setRecommendMovieLocalUseCase(recommendationMovieList.map {
                RecommendMovie(it.id.toLong(), it.poster_path, it.title)
            })
        }
    }

    private fun saveLocalTopRatedMovies(topRatedList: List<Movie>) {
        viewModelScope.launch(Dispatchers.IO)  {
            setTopRatedMovieLocalUseCase(
                topRatedList.map { TopRatedMovie(it.id.toLong(), it.poster_path, it.title) }
            )
        }
    }

}

