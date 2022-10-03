package com.zaki.movieapp

import com.google.common.truth.Truth.assertThat
import com.zaki.movieapp.data.local.entitiy.MovieTrendingEntity
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.mapper.MovieMapper.toEntity
import com.zaki.movieapp.mapper.MovieMapper.toMovieTrending
import io.mockk.mockkStatic
import org.junit.Test

class MovieMapperTest {

    @Test
    fun testToEntity() {
        val movie = MovieTrending(
            id = 4123,
            title = "Avenger",
            overview = "Cool Movie",
            releaseDate = "2022-09-26",
            posterPath = "google.com",
            voteAverage = 8.9,
            isBookmarked = false
        )
        val movieEntity = movie.toEntity()

        assertThat(movieEntity).isInstanceOf(MovieTrendingEntity::class.java)
        assert(movie.id == movieEntity.id)
        assert(movie.title == movieEntity.title)
        assert(movie.overview == movieEntity.overview)
        assert(movie.releaseDate == movieEntity.releaseDate)
        assert(movie.posterPath == movieEntity.posterPath)
        assert(movie.voteAverage == movieEntity.voteAverage)
        assert(movie.isBookmarked == movieEntity.isFavorite)
    }

    @Test
    fun testToMovieTrending() {
        val movieEntity = MovieTrendingEntity(
            id = 4123,
            title = "Avenger",
            overview = "Cool Movie",
            releaseDate = "2022-09-26",
            posterPath = "google.com",
            voteAverage = 8.9,
            isFavorite = false
        )
        val movieTrending = movieEntity.toMovieTrending()

        assertThat(movieTrending).isInstanceOf(MovieTrending::class.java)
        assert(movieEntity.id == movieTrending.id)
        assert(movieEntity.title == movieTrending.title)
        assert(movieEntity.overview == movieTrending.overview)
        assert(movieEntity.releaseDate == movieTrending.releaseDate)
        assert(movieEntity.posterPath == movieTrending.posterPath)
        assert(movieEntity.voteAverage == movieTrending.voteAverage)
        assert(movieEntity.isFavorite == movieTrending.isBookmarked)
    }
}