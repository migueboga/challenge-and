package com.op.storage.domain.entity

data class Review(
    val id: String,
    val title: String,
    val overview: String,
    val posterPath: String,
    val voteCount: String
)
