package com.op.movies.presentation.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.op.moviedb.domain.entity.Person
import com.op.moviedb.domain.usecase.GetPopularUseCase
import com.op.movies.R
import com.op.movies.presentation.base.BaseViewModel
import com.op.storage.data.database.entity.Profile
import com.op.storage.domain.entity.Review
import com.op.storage.domain.usecase.GetProfileLocalUseCase
import com.op.storage.domain.usecase.SetProfileLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getPopularUseCase: GetPopularUseCase,
    private val getProfileLocalUseCase: GetProfileLocalUseCase,
    private val setProfileLocalUseCase: SetProfileLocalUseCase
) : BaseViewModel<ProfileUiState>(ProfileUiState()) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPopular() {
        viewModelScope.launch {
            _uiState.postValue(_uiState.value?.copy(isLoading = true))
            getProfileLocalUseCase()
                .distinctUntilChanged()
                .flatMapConcat {
                    it?.let { localProfile ->
                        _uiState.postValue(
                            _uiState.value?.copy(
                                isLoading = false,
                                name = localProfile.name,
                                profilePath = localProfile.profilePath,
                                popularity = localProfile.popularity,
                                id = localProfile.id.toString(),
                                reviews = localProfile.reviews.map {
                                    ReviewUiState(
                                        it.id,
                                        it.title,
                                        it.overview,
                                        it.posterPath,
                                        it.voteCount
                                    )
                                }
                            )
                        )
                    }
                    getPopularUseCase()
                }
                .catch {
                    Log.e("debug", it.localizedMessage, it)
                    _uiState.postValue(
                        _uiState.value?.copy(
                            isLoading = false, error = R.string.err_get_popular_person
                        )
                    )
                }
                .collect { response ->
                    saveLocalProfile(response)
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

    override fun clearError() {
        _uiState.postValue(
            _uiState.value?.copy(error = null)
        )
    }

    private fun saveLocalProfile(profile: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            setProfileLocalUseCase(
                Profile(
                    profile.id.toLong(),
                    profile.name,
                    profile.profile_path,
                    profile.popularity.toString(),
                    profile.known_for.map {
                        Review(
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

