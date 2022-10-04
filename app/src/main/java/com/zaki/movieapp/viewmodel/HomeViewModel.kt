package com.zaki.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaki.movieapp.data.local.entitiy.MovieTrendingEntity
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _homeUiState: MutableLiveData<HomeUiState> = MutableLiveData(HomeUiState.Initial)
    val homeUiState: LiveData<HomeUiState> = _homeUiState

    fun getMovies() {
        _homeUiState.postValue(HomeUiState.Loading)
        movieRepository.getMovies()
            .doOnError {
                _homeUiState.postValue(HomeUiState.Error)
            }
            .subscribe {
                _homeUiState.postValue(HomeUiState.ShowMovies(it))
            }
    }
}

sealed class HomeUiState {
    object Initial: HomeUiState()
    object Loading: HomeUiState()
    data class ShowMovies(val movies: List<MovieTrending>): HomeUiState()
    object Error: HomeUiState()
}