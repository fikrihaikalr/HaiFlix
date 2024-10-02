package com.fikrihaikal.haiflix.core.di

import com.fikrihaikal.haiflix.core.data.source.repository.MovieRepository
import com.fikrihaikal.haiflix.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieRepository: MovieRepository): IMovieRepository
}