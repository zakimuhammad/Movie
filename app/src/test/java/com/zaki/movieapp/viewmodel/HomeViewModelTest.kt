package com.zaki.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.repository.MovieRepository
import com.zaki.movieapp.helper.Result
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi class HomeViewModelTest {

  @get:Rule var testRule: TestRule = InstantTaskExecutorRule()

  @MockK lateinit var movieRepository: MovieRepository

  private lateinit var viewModel: HomeViewModel

  private val testDispatcher = UnconfinedTestDispatcher()

  @Before fun setup() {
    MockKAnnotations.init(this)
    Dispatchers.setMain(testDispatcher)
    viewModel = HomeViewModel(movieRepository, testDispatcher)
  }

  @After fun tearDown() {
    Dispatchers.resetMain()
    clearAllMocks()
  }

  @Test fun `do nothing then homeUiState is initial`() {
    assertThat(viewModel.homeUiState.value).isEqualTo(HomeUiState.Initial)
  }

  @Test
  fun `given data when call getMovies then homeUiState value should success with data and verify insert movie called`() {
    val data = listOf(MovieTrending(id = 132), MovieTrending(id = 9213))
    every { movieRepository.getMovies() } returns Observable.create {
      it.onNext(Result.Success(data))
    }

    viewModel.getMovies()

    assertThat(viewModel.homeUiState.value).isEqualTo(HomeUiState.ShowMovies(data))

    coVerify {
      movieRepository.insertMovies(any())
      movieRepository.getMovies()
    }
  }

  @Test fun `given nothing when call getMovies then homeUiState value should loading`() {
    every { movieRepository.getMovies() } returns Observable.create {
      it.onNext(Result.Loading)
    }

    viewModel.getMovies()

    assertThat(viewModel.homeUiState.value).isEqualTo(HomeUiState.Loading)
  }

  @Test fun `given error when call getMovies then homeUiState value should error`() {
    every { movieRepository.getMovies() } returns Observable.create {
      it.onNext(Result.Error("Something Wrong"))
    }

    viewModel.getMovies()

    assertThat(viewModel.homeUiState.value).isEqualTo(HomeUiState.Error("Something Wrong"))
  }
}