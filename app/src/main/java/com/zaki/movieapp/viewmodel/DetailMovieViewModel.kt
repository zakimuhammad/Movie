package com.zaki.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.repository.MovieRepository
import com.zaki.movieapp.mapper.MovieMapper.toMovieFavoriteEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailMovieViewModel @Inject constructor(
  private val movieRepository: MovieRepository,
  private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

  private val _favoriteState: MutableLiveData<Boolean> = MutableLiveData()
  val favoriteState: LiveData<Boolean> = _favoriteState

  fun getFavoriteMovie(movieTrending: MovieTrending) {
    movieRepository.getFavoriteMovie(movieTrending.id ?: 0)
      .subscribe {
        _favoriteState.value = it.isNotEmpty()
      }
  }

  fun updateFavoriteMovie(movie: MovieTrending) = viewModelScope.launch(ioDispatcher) {
    if (favoriteState.value == true) {
      movieRepository.deleteMovie(movie.toMovieFavoriteEntity())
    } else {
      movieRepository.insertMovie(movie.toMovieFavoriteEntity())
    }
  }
}