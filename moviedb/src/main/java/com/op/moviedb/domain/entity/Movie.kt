package com.op.moviedb.domain.entity

data class Movie(
    val adult: Boolean,
    val backdrop_path: String? = null,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Double
)
