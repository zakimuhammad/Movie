package com.zaki.movieapp.view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.databinding.ItemMovieBinding
import com.zaki.movieapp.helper.OnMovieClickListener
import com.zaki.movieapp.util.DateUtil

class MovieViewHolder(
  private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(movie: MovieTrending, onMovieClickListener: OnMovieClickListener) {
    binding.tvTitle.text = movie.title
    binding.tvOverview.text = movie.overview
    binding.tvReleaseDate.text = DateUtil.convertDate(movie.releaseDate.orEmpty())
    binding.tvVoteAverage.text = String.format("%.1f", movie.voteAverage)
    Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
      .into(binding.ivMovie)

    itemView.setOnClickListener {
      onMovieClickListener.onClickItem(movie)
    }
  }
}