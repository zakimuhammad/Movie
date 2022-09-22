package com.zaki.movieapp.view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.databinding.ItemMovieBinding

class MovieViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieTrending) {
        binding.tvTitle.text = if (movie.title?.isEmpty() == true) movie.name else movie.title
        binding.tvOverview.text = movie.overview
        Glide.with(itemView.context)
            .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
            .into(binding.ivMovie)
    }
}