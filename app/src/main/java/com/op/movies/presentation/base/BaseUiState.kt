package com.op.movies.presentation.base

abstract class BaseUiState {
    abstract val isLoading: Boolean
    abstract val error: Int?
    abstract val message: Int?
}