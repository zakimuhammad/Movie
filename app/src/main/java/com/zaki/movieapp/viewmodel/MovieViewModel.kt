package com.zaki.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieViewModel @Inject constructor(
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