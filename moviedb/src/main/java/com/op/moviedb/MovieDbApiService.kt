package com.op.moviedb

import com.op.moviedb.domain.entity.PopularMovieResponse
import com.op.moviedb.domain.entity.PopularResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDbApiService {

    @GET("/3/person/popular")
    suspend fun getPopular(): PopularResponse

    @GET("/3/movie/popular")
    suspend fun getPopularMovie(): PopularMovieResponse

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovie(): PopularMovieResponse // TODO same as before, change name

    @GET("/3/movie/{movie_id}/recommendations")
    suspend fun getRecommendationsByMovieId(
        @Path("movie_id") movieId: String
    ): PopularMovieResponse // TODO same as before, change name

}