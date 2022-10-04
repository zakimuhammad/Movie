package com.zaki.movieapp.helper

import com.zaki.movieapp.data.remote.response.MovieTrending

interface OnMovieClickListener {
    fun onClickItem(movieTrending: MovieTrending)
}