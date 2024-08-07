package com.op.movies.presentation.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.op.moviedb.domain.usecase.GetPopularUseCase
import com.op.movies.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getPopularUseCase: GetPopularUseCase
) : BaseViewModel<ProfileUiState>(ProfileUiState()) {

    fun getPopular() {
        viewModelScope.launch {
            _uiState.postValue(_uiState.value?.copy(isLoading = true))
            getPopularUseCase()
                .catch {
                    Log.e("debug", it.localizedMessage, it)
                    _uiState.postValue(
                        _uiState.value?.copy(
                            isLoading = false, error = R.string.general_error
                        )
                    )
                }
                .collect { response ->
                    _uiState.postValue(
                        _uiState.value?.copy(
                            isLoading = false,
                            name = response.name,
                            profilePath = response.profile_path,
                            popularity = response.popularity.toString(),
                            id = response.id.toString(),
                            reviews = response.known_for.map {
                                ReviewUiState(
                                    it.id.toString(),
                                    it.title,
                                    it.overview,
                                    it.poster_path,
                                    it.vote_count.toString()
                                )
                            }
                        )
                    )
                }
        }
    }

}

abstract class BaseViewModel<I : BaseUiState>(value: I): ViewModel() {

    protected val _uiState = MutableLiveData(value)
    val uiState: LiveData<I> get() = _uiState

}

abstract class BaseUiState {
    abstract val isLoading: Boolean
    abstract val error: Int?
    abstract val message: Int?
}