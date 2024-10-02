package com.fikrihaikal.haiflix.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.haiflix.core.data.source.Resource
import com.fikrihaikal.haiflix.core.domain.model.Caster
import com.fikrihaikal.haiflix.core.domain.model.Movie
import com.fikrihaikal.haiflix.core.domain.model.MovieVideo
import com.fikrihaikal.haiflix.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _castResponse = MutableStateFlow<Resource<List<Caster>>>(Resource.Loading())
    val castResponse: StateFlow<Resource<List<Caster>>> = _castResponse.asStateFlow()

    fun getCastAndCrew(id:Int) = viewModelScope.launch(Dispatchers.IO) {
        movieUseCase.getCastAndCrew(id).collect{ result ->
            _castResponse.value = result
        }
    }

    private val _movieVideoResponse = MutableStateFlow<Resource<List<MovieVideo>>>(Resource.Loading())
    val movieVideoResponse: StateFlow<Resource<List<MovieVideo>>> = _movieVideoResponse.asStateFlow()

    fun getMovieVideo(id:Int) = viewModelScope.launch(Dispatchers.IO) {
        movieUseCase.getMovieVideoById(id).collect{result ->
            _movieVideoResponse.value = result
        }
    }
    fun insertMovie(movie: Movie) = viewModelScope.launch {
        movieUseCase.insertMovie(movie)
    }
    fun deleteMovie(movie:Movie) = viewModelScope.launch {
        movieUseCase.delete(movie)
    }
    fun isFavorite(id:Int) = movieUseCase.isFavorite(id).asLiveData()
}