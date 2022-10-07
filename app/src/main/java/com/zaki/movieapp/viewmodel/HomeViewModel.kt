package com.zaki.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.repository.MovieRepository
import com.zaki.movieapp.helper.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
  private val movieRepository: MovieRepository, private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _homeUiState: MutableLiveData<HomeUiState> = MutableLiveData(HomeUiState.Initial)
  val homeUiState: LiveData<HomeUiState> = _homeUiState

  fun getMovies() {
    _homeUiState.postValue(HomeUiState.Loading)
    movieRepository.getMovies().subscribe({ result ->
        when (result) {
          is Result.Error -> {
            _homeUiState.postValue(HomeUiState.Error(result.message))
          }
          is Result.Success -> {
            _homeUiState.postValue(HomeUiState.ShowMovies(result.data))
          }
        }
      }, { error ->
        _homeUiState.postValue(HomeUiState.Error(error.message.orEmpty()))
      })
  }
}

sealed class HomeUiState {
  object Initial : HomeUiState()
  object Loading : HomeUiState()
  data class ShowMovies(val movies: List<MovieTrending>) : HomeUiState()
  data class Error(val message: String) : HomeUiState()
}