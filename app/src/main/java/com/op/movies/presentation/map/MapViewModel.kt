package com.op.movies.presentation.map

import com.op.movies.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(): BaseViewModel<MapUiState>(MapUiState()) {

    override fun clearError() {

    }

}