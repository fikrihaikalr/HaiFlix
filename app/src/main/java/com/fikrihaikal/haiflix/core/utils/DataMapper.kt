package com.fikrihaikal.haiflix.core.utils


import com.fikrihaikal.haiflix.core.data.source.local.entity.MovieEntity
import com.fikrihaikal.haiflix.core.data.source.remote.response.CastItem
import com.fikrihaikal.haiflix.core.data.source.remote.response.GetMovieResponse
import com.fikrihaikal.haiflix.core.data.source.remote.response.Result
import com.fikrihaikal.haiflix.core.data.source.remote.response.ResultsItem
import com.fikrihaikal.haiflix.core.domain.model.Caster
import com.fikrihaikal.haiflix.core.domain.model.Movie
import com.fikrihaikal.haiflix.core.domain.model.MovieVideo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    private const val PATH_IMG = "https://image.tmdb.org/t/p/w500"

    fun mapListResponseToDomain(movieResponse: List<Result>): Flow<List<Movie>> {
        val movieList = ArrayList<Movie>()
        movieResponse.map {
            val movie = Movie(
                PATH_IMG+it.posterPath,
                it.title,
                it.id,
                it.overview,
                it.voteAverage,
                it.voteCount.toDouble(),
                it.runtime,
                it.releaseDate,
                false
            )
            movieList.add(movie)
        }
        return flowOf(movieList)
    }

    fun mapListCastAndCrewResponseToDomain(casterResponse: List<CastItem>): Flow<List<Caster>>{
        val casterList = ArrayList<Caster>()
        casterResponse.map {
            val caster = Caster(
                it.id,
                it.name,
                PATH_IMG+it.profilePath
            )
            casterList.add(caster)
        }
        return flowOf(casterList)
    }

    fun mapListMovieVideoToDomain(movieVideoResponse: List<ResultsItem>): Flow<List<MovieVideo>>{
        val movieVideoList = ArrayList<MovieVideo>()
        movieVideoResponse.map {
            val movieVideo = MovieVideo(
                it.iso6391,
                it.iso31661,
                it.name,
                it.key,
                it.site,
                it.size,
                it.type,
                it.official,
                it.publishedAt,
                it.id
            )
            movieVideoList.add(movieVideo)
        }
        return flowOf(movieVideoList)
    }

    fun mapDomainToEntity(movie:Movie) =
        MovieEntity(
            movie.id,
            movie.img,
            movie.name,
            movie.overview,
            movie.voteAverage,
            movie.voteCount,
            movie.runtime,
            movie.releaseDate,
            movie.isFavorite
        )

    fun mapListEntityToDomain(listMovieEntity: List<MovieEntity>): List<Movie> =
        listMovieEntity.map {
            Movie(
                it.img,
                it.name,
                it.id,
                it.overview,
                it.voteAverage,
                it.voteCount,
                it.runtime,
                it.releaseDate,
                it.isFavorite
            )
        }
}