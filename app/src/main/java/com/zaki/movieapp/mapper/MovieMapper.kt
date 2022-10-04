package com.zaki.movieapp.mapper

import com.zaki.movieapp.data.local.entitiy.MovieFavoriteEntity
import com.zaki.movieapp.data.local.entitiy.MovieTrendingEntity
import com.zaki.movieapp.data.remote.response.MovieTrending

object MovieMapper {

    fun MovieTrending.toEntity() =  MovieTrendingEntity(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        voteAverage = voteAverage
    )

    fun MovieTrendingEntity.toMovieTrending() = MovieTrending(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        voteAverage = voteAverage
    )

    fun MovieFavoriteEntity.toMovieTrending() = MovieTrending(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        voteAverage = voteAverage,
    )

    fun MovieTrending.toMovieFavoriteEntity() = MovieFavoriteEntity(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        voteAverage = voteAverage,
    )
}