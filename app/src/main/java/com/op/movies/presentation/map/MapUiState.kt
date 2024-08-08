package com.op.movies.presentation.map

import com.op.movies.presentation.base.BaseUiState

data class MapUiState(
    override val error: Int? = null,
    override val isLoading: Boolean = false,
    override val message: Int? = null,
): BaseUiState()
