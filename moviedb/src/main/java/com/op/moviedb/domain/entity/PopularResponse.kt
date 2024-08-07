package com.op.moviedb.domain.entity

data class PopularResponse(
    val page: Int,
    val results: List<Person>,
    val total_pages: Int
)
