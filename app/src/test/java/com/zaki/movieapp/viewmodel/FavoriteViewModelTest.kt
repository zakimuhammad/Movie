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
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class FavoriteViewModelTest {

    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: FavoriteViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = FavoriteViewModel(movieRepository)
    }

    @After
    fun tearDown() {
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