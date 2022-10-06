package com.zaki.movieapp

import com.google.common.truth.Truth.assertThat
import com.zaki.movieapp.data.local.entitiy.MovieFavoriteEntity
import com.zaki.movieapp.data.local.entitiy.MovieTrendingEntity
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.mapper.MovieMapper.toEntity
import com.zaki.movieapp.mapper.MovieMapper.toMovieFavoriteEntity
import com.zaki.movieapp.mapper.MovieMapper.toMovieTrending
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
            voteAverage = 8.9
        )
        val movieEntity = movie.toEntity()

        assertThat(movieEntity).isInstanceOf(MovieTrendingEntity::class.java)
        assert(movie.id == movieEntity.id)
        assert(movie.title == movieEntity.title)
        assert(movie.overview == movieEntity.overview)
        assert(movie.releaseDate == movieEntity.releaseDate)
        assert(movie.posterPath == movieEntity.posterPath)
        assert(movie.voteAverage == movieEntity.voteAverage)
    }

    @Test
    fun testToMovieTrending() {
        val movieEntity = MovieTrendingEntity(
            id = 4123,
            title = "Avenger",
            overview = "Cool Movie",
            releaseDate = "2022-09-26",
            posterPath = "google.com",
            voteAverage = 8.9
        )
        val movieTrending = movieEntity.toMovieTrending()

        assertThat(movieTrending).isInstanceOf(MovieTrending::class.java)
        assert(movieEntity.id == movieTrending.id)
        assert(movieEntity.title == movieTrending.title)
        assert(movieEntity.overview == movieTrending.overview)
        assert(movieEntity.releaseDate == movieTrending.releaseDate)
        assert(movieEntity.posterPath == movieTrending.posterPath)
        assert(movieEntity.voteAverage == movieTrending.voteAverage)
    }

    @Test
    fun testFromMovieFavoriteEntityToMovieTrending() {
        val movieEntity = MovieFavoriteEntity(
            id = 4123,
            title = "Avenger",
            overview = "Cool Movie",
            releaseDate = "2022-09-26",
            posterPath = "google.com",
            voteAverage = 8.9
        )
        val movieTrending = movieEntity.toMovieTrending()

        assertThat(movieTrending).isInstanceOf(MovieTrending::class.java)
        assert(movieEntity.id == movieTrending.id)
        assert(movieEntity.title == movieTrending.title)
        assert(movieEntity.overview == movieTrending.overview)
        assert(movieEntity.releaseDate == movieTrending.releaseDate)
        assert(movieEntity.posterPath == movieTrending.posterPath)
        assert(movieEntity.voteAverage == movieTrending.voteAverage)
    }

    @Test
    fun testFromMovieTrendingToMovieFavoriteEntity() {
        val movie = MovieTrending(
            id = 4123,
            title = "Avenger",
            overview = "Cool Movie",
            releaseDate = "2022-09-26",
            posterPath = "google.com",
            voteAverage = 8.9
        )
        val movieEntity = movie.toMovieFavoriteEntity()

        assertThat(movieEntity).isInstanceOf(MovieFavoriteEntity::class.java)
        assert(movie.id == movieEntity.id)
        assert(movie.title == movieEntity.title)
        assert(movie.overview == movieEntity.overview)
        assert(movie.releaseDate == movieEntity.releaseDate)
        assert(movie.posterPath == movieEntity.posterPath)
        assert(movie.voteAverage == movieEntity.voteAverage)
    }
}