package com.zaki.movieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.repository.MovieRepository
import com.zaki.movieapp.viewmodel.HomeUiState
import com.zaki.movieapp.viewmodel.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {

    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(movieRepository)
    }

    @Test
    fun `do nothing then homeUiState is initial`() {
        assertThat(viewModel.homeUiState.value).isEqualTo(HomeUiState.Initial)
    }

    @Test
    fun `given data when call getMovies then homeUiState value should success with data`() {
        val data = listOf(MovieTrending(id = 132), MovieTrending(id = 9213))
        every { movieRepository.getMovies() } returns Observable.create {
            it.onNext(data)
        }

        viewModel.getMovies()

        assertThat(viewModel.homeUiState.value).isEqualTo(HomeUiState.ShowMovies(data))
    }

    @Test
    fun `given nothing when call getMovies then homeUiState value should loading`() {
        every { movieRepository.getMovies() } returns Observable.create {
            it.onComplete()
        }

        viewModel.getMovies()

        assertThat(viewModel.homeUiState.value).isEqualTo(HomeUiState.Loading)
    }

    @Test
    fun `given error when call getMovies then homeUiState value should error`() {
        every { movieRepository.getMovies() } returns Observable.create {
            it.onError(Throwable())
        }

        viewModel.getMovies()

        assertThat(viewModel.homeUiState.value).isEqualTo(HomeUiState.Error)
    }
}