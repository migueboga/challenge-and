package com.op.movies.presentation.gallery

import android.net.Uri
import com.op.movies.presentation.base.BaseUiState

data class GalleryUiState(
    val imageUriList: List<Uri> = listOf(),
    override val error: Int? = null,
    override val isLoading: Boolean = false,
    override val message: Int? = null
): BaseUiState()
