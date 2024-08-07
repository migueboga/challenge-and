package com.op.moviedb

import com.op.moviedb.domain.entity.PopularResponse
import retrofit2.http.GET

interface MovieDbApiService {

    @GET("/3/person/popular")
    suspend fun getPopular(): PopularResponse

}