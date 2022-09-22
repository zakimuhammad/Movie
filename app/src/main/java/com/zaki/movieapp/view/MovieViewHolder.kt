package com.zaki.movieapp.view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaki.movieapp.MovieApplication
import com.zaki.movieapp.R
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.databinding.ItemMovieBinding
import com.zaki.movieapp.domain.DateUseCase
import javax.inject.Inject

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val dateUseCase: DateUseCase
): RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieTrending) {
        binding.tvTitle.text = movie.title
        binding.tvOverview.text = movie.overview
        binding.tvReleaseDate.text = dateUseCase.convertDate(movie.releaseDate.orEmpty())
        binding.tvVoteAverage.text = String.format("%.1f", movie.voteAverage)
        Glide.with(itemView.context)
            .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
            .into(binding.ivMovie)
    }
}