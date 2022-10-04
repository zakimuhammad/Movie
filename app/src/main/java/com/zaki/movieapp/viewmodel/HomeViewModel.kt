package com.zaki.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.repository.MovieRepository
import com.zaki.movieapp.helper.Result
import retrofit2.HttpException
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {

    private val _homeUiState: MutableLiveData<HomeUiState> = MutableLiveData(HomeUiState.Initial)
    val homeUiState: LiveData<HomeUiState> = _homeUiState

    fun getMovies() {
        _homeUiState.postValue(HomeUiState.Loading)
        movieRepository.getMovies()
            .subscribe({ result ->
                when (result) {
                    is Result.Error -> _homeUiState.postValue(HomeUiState.Error(result.message))
                    is Result.Success -> _homeUiState.postValue(HomeUiState.ShowMovies(result.data))
                }
            }, { error ->
                if (error is HttpException) {
                    _homeUiState.postValue(HomeUiState.Error(error.message()))
                } else {
                    _homeUiState.postValue(HomeUiState.Error(error.localizedMessage.orEmpty()))
                }
            })
    }
}

sealed class HomeUiState {
    object Initial: HomeUiState()
    object Loading: HomeUiState()
    data class ShowMovies(val movies: List<MovieTrending>): HomeUiState()
    data class Error(val message: String): HomeUiState()
}