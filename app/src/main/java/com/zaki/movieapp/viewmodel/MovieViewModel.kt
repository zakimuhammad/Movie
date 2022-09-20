package com.zaki.movieapp.viewmodel

import com.zaki.movieapp.data.repository.MovieRepository
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) {

}