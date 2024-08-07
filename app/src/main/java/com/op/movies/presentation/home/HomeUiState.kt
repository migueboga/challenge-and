package com.op.movies.presentation.home

import com.op.movies.presentation.base.BaseUiState

data class HomeUiState(
    val popularMovieList: List<MovieUiState> = listOf(),
    val topRatedMovieList: List<MovieUiState> = listOf(),
    val recommendationList: List<MovieUiState> = listOf(),
    override val error: Int? = null,
    override val isLoading: Boolean = false,
    override val message: Int? = null
): BaseUiState()