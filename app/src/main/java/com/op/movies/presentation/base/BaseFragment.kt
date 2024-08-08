package com.op.movies.presentation.base

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    protected abstract fun collectUiState()

}