package com.fikrihaikal.haiflix.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.haiflix.core.domain.model.Movie
import com.fikrihaikal.haiflix.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    val getAllFavorite = movieUseCase.getAllFavorite().asLiveData()

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        movieUseCase.delete(movie)
    }
}