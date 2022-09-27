package com.zaki.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
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

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {

    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: FavoriteViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = FavoriteViewModel(movieRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `given data when call getFavoriteMovies then movies should have values`() {
        val movies = listOf(MovieTrending(id = 912312), MovieTrending(id = 1223))
        every { movieRepository.getFavoriteMovies() } returns Observable.create {
            it.onNext(movies)
        }

        viewModel.getFavoriteMovies()

        assertThat(viewModel.movies.value).isEqualTo(movies)
    }
}