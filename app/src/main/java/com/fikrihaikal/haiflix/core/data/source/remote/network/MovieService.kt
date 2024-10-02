package com.fikrihaikal.haiflix.core.data.source.remote.network

import com.fikrihaikal.haiflix.core.data.source.remote.network.MovieService.Companion.api_key
import com.fikrihaikal.haiflix.core.data.source.remote.response.GetCastAndCrewResponse
import com.fikrihaikal.haiflix.core.data.source.remote.response.GetMovieResponse
import com.fikrihaikal.haiflix.core.data.source.remote.response.GetMovieVideoByIdResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("discover/movie?api_key=$api_key")
    suspend fun getMovieDiscover(): GetMovieResponse

    @GET("movie/{id}?api_key=$api_key")
    suspend fun getMovieDetailById(
        @Path("id") id:Int
    ): GetMovieResponse

    @GET("movie/{id}/credits?api_key=$api_key")
    suspend fun getCastAndCrew(
        @Path("id") id:Int
    ):GetCastAndCrewResponse

    @GET("movie/{id}/videos?api_key=$api_key")
    suspend fun getMovieVideoById(
        @Path("id") id:Int
    ):GetMovieVideoByIdResponse

    companion object {
        private const val api_key = "ced9627fcb3b480331b91e3223e07ac4"
    }
}