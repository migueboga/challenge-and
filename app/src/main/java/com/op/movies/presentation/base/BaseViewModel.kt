package com.op.movies.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<I : BaseUiState>(value: I): ViewModel() {

    protected val _uiState = MutableLiveData(value)
    val uiState: LiveData<I> get() = _uiState

    abstract fun clearError()

}