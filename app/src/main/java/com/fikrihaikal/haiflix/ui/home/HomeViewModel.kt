package com.fikrihaikal.haiflix.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.haiflix.core.data.source.Resource
import com.fikrihaikal.haiflix.core.data.source.remote.response.GetMovieResponse
import com.fikrihaikal.haiflix.core.domain.model.Movie
import com.fikrihaikal.haiflix.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

//    private val _discoverMovies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
//    val discoverMovies: StateFlow<Resource<List<Movie>>> = _discoverMovies.asStateFlow()
//
//    fun getDiscoverMovies(){
//        viewModelScope.launch {
//            movieUseCase.getMovieDiscover().collect{result ->
//                _discoverMovies.value = result
//            }
//        }
//    }
    val getMovie = movieUseCase.getMovieDiscover().asLiveData()
}