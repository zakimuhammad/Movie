package com.zaki.movieapp.helper

import com.zaki.movieapp.data.remote.response.MovieTrending

interface OnMovieClickListener {
    fun onClickBookmark(movieTrending: MovieTrending)
    fun onClickItem(movieTrending: MovieTrending)
}