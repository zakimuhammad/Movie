package com.zaki.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaki.movieapp.data.local.entitiy.MovieTrendingEntity
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
): ViewModel() {

    val movies: MutableLiveData<List<MovieTrending>> = MutableLiveData()

    fun getMovies() {
        movieRepository.getMovies()
            .doOnError {
                Log.e("ERROR", it.localizedMessage.orEmpty())
            }
            .subscribe {
                movies.postValue(it)
            }
    }

    fun bookmarkMovie(movie: MovieTrendingEntity) = viewModelScope.launch(Dispatchers.IO) {
        movieRepository.updateMovie(movie.copy(isFavorite = movie.isFavorite.not()))
    }
}