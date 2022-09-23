package com.zaki.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.repository.MovieRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {

    val movies: MutableLiveData<List<MovieTrending>> = MutableLiveData()

    fun getMovies() {
        movieRepository.getMovies()
            .debounce(500, TimeUnit.MILLISECONDS)
            .doOnError {
                Log.e("ERROR", it.localizedMessage.orEmpty())
            }
            .subscribe {
                movies.postValue(it)
            }
    }
}