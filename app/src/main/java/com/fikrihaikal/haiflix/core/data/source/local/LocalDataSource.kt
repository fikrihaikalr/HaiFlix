package com.fikrihaikal.haiflix.core.data.source.local

import com.fikrihaikal.haiflix.core.data.source.local.entity.MovieEntity
import com.fikrihaikal.haiflix.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {
    suspend fun insertMovie(movieEntity: MovieEntity) = movieDao.insertMovie(movieEntity)
    fun getAllFavorite(): Flow<List<MovieEntity>> = movieDao.getAllFavorite()
    suspend fun delete(movieEntity: MovieEntity) = movieDao.delete(movieEntity)
    fun isFavorite(id: Int): Flow<Boolean> = movieDao.isFavorite(id)
}