package com.op.movies.presentation.gallery

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.op.firebase.storage.domain.usecase.GetImageFileReferencesUseCase
import com.op.firebase.storage.domain.usecase.UploadImageFileUseCase
import com.op.movies.R
import com.op.movies.presentation.base.BaseViewModel
import com.op.movies.util.toFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val uploadImageFileUseCase: UploadImageFileUseCase,
    private val getImageFileReferencesUseCase: GetImageFileReferencesUseCase
) : BaseViewModel<GalleryUiState>(GalleryUiState()) {


    fun requestImages() {
        viewModelScope.launch {
            _uiState.postValue(
                _uiState.value?.copy(imageUriList = emptyList())
            )
            getImageFileReferencesUseCase()
                .catch {
                    Log.e("debug", it.localizedMessage, it)
                    _uiState.postValue(
                        _uiState.value?.copy(error = R.string.err_get_image)
                    )
                }
                .collect { fileReferences ->
                    fileReferences.forEach { fileRef ->
                        val downloadUrl = fileRef.downloadUrl.await()
                        _uiState.postValue(
                            _uiState.value?.copy(
                                imageUriList = _uiState.value?.imageUriList.orEmpty() + downloadUrl
                            )
                        )
                    }
                }
        }
    }

    fun uploadImage(uri: Uri, context: Context) {
        uri.toFile(context)?.let { file ->
            viewModelScope.launch {
                uploadImageFileUseCase(file)
                    .catch {
                        Log.e("debug", it.localizedMessage, it)
                        _uiState.postValue(
                            _uiState.value?.copy(error = R.string.err_upload_image)
                        )
                    }
                    .collect { _ ->
                        requestImages()
                    }
            }
        }
    }

    override fun clearError() {
        _uiState.postValue(
            _uiState.value?.copy(error = null)
        )
    }

}