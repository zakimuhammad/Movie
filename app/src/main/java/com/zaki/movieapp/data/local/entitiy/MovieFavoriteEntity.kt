package com.zaki.movieapp.data.local.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
  tableName = "movie_favorites"
)
data class MovieFavoriteEntity(
  @PrimaryKey val id: Int? = null,
  val title: String? = null,
  val overview: String? = null,
  @ColumnInfo(name = "release_date") val releaseDate: String? = null,
  @ColumnInfo(name = "poster_path") val posterPath: String? = null,
  @ColumnInfo(name = "vote_average") val voteAverage: Double? = null,
)