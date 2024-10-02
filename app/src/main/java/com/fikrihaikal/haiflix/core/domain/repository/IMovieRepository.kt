package com.fikrihaikal.haiflix.core.domain.repository

import com.fikrihaikal.haiflix.core.data.source.Resource
import com.fikrihaikal.haiflix.core.domain.model.Caster
import com.fikrihaikal.haiflix.core.domain.model.Movie
import com.fikrihaikal.haiflix.core.domain.model.MovieVideo
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovieDiscover(): Flow<Resource<List<Movie>>>
    fun getCastAndCrew(id:Int): Flow<Resource<List<Caster>>>
    fun getMovieVideoById(id: Int): Flow<Resource<List<MovieVideo>>>
    suspend fun insertMovie(movie: Movie)
    fun getAllFavorite(): Flow<List<Movie>>
    suspend fun delete(movie: Movie)
    fun isFavorite(id:Int): Flow<Boolean>
}