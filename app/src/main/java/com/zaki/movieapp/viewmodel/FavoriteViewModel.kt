package com.zaki.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
  private val movieRepository: MovieRepository, private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
  private val _movies: MutableLiveData<List<MovieTrending>> = MutableLiveData()
  val movies: LiveData<List<MovieTrending>>
    get() = _movies

  fun getFavoriteMovies() {
    movieRepository.getFavoriteMovies().subscribe {
        _movies.postValue(it)
      }
  }
}