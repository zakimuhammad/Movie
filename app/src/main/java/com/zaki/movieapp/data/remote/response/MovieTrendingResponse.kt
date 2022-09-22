package com.zaki.movieapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieTrendingResponse(
    @SerializedName("results") val results: List<MovieTrending>? = null
)

data class MovieTrending(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null
)
