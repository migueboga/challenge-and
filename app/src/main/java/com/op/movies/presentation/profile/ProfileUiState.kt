package com.op.movies.presentation.profile

data class ProfileUiState(
    val name: String = "",
    val profilePath: String = "",
    val popularity: String = "",
    val id: String = "",
    val reviews: List<ReviewUiState> = listOf(),
    override val error: Int? = null,
    override val isLoading: Boolean = false,
    override val message: Int? = null
): BaseUiState()
