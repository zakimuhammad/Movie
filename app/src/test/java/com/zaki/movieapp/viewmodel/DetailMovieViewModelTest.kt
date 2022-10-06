package com.zaki.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.repository.MovieRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi class DetailMovieViewModelTest {

  @get:Rule var testRule: TestRule = InstantTaskExecutorRule()

  @MockK lateinit var movieRepository: MovieRepository

  private lateinit var viewModel: DetailMovieViewModel

  private val testDispatcher = UnconfinedTestDispatcher()

  @Before fun setup() {
    MockKAnnotations.init(this)
    viewModel = DetailMovieViewModel(movieRepository, testDispatcher)
  }

  @After fun tearDown() {
    clearAllMocks()
  }

  @Test fun `given data when call get favorite then favoriteState should true`() {
    every { movieRepository.getFavoriteMovie(any()) } returns Observable.create {
      it.onNext(listOf(MovieTrending()))
    }

    viewModel.getFavoriteMovie(MovieTrending())

    assertThat(viewModel.favoriteState.value).isTrue()

    verify { movieRepository.getFavoriteMovie(any()) }
  }

  @Test fun `given empty data when call get favorite then favoriteState should false`() {
    every { movieRepository.getFavoriteMovie(any()) } returns Observable.create {
      it.onNext(listOf())
    }

    viewModel.getFavoriteMovie(MovieTrending())

    assertThat(viewModel.favoriteState.value).isFalse()

    verify { movieRepository.getFavoriteMovie(any()) }
  }

  @Test
  fun `call function updateFavoriteMovie with favoriteState is true then verify deleteMovie called`() {
    every { movieRepository.getFavoriteMovie(any()) } returns Observable.create {
      it.onNext(listOf(MovieTrending()))
    }

    viewModel.getFavoriteMovie(MovieTrending())
    viewModel.updateFavoriteMovie(MovieTrending())

    coVerify { movieRepository.deleteMovie(any()) }
    verify { movieRepository.getFavoriteMovie(any()) }
  }

  @Test
  fun `call function updateFavoriteMovie with favoriteState is false then verify insertMovie called`() {
    every { movieRepository.getFavoriteMovie(any()) } returns Observable.create {
      it.onNext(listOf())
    }

    viewModel.getFavoriteMovie(MovieTrending())
    viewModel.updateFavoriteMovie(MovieTrending())

    coVerify { movieRepository.insertMovie(any()) }
    verify { movieRepository.getFavoriteMovie(any()) }
  }
}