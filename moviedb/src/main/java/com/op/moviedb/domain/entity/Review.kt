package com.op.moviedb.domain.entity

data class Review(
    val backdrop_path: String,
    val id: Int,
    val title: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val media_type: String,
    val adult: Boolean,
    val original_language: String,
    val popularity: Double,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Double,
)
