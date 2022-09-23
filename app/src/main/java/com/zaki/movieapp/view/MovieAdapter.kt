package com.zaki.movieapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.databinding.ItemMovieBinding
import com.zaki.movieapp.domain.DateUseCase
import com.zaki.movieapp.helper.OnMovieClickListener
import javax.inject.Inject

class MovieAdapter @Inject constructor(
    private val dateUseCase: DateUseCase
): RecyclerView.Adapter<MovieViewHolder>() {

    private val movies = mutableListOf<MovieTrending>()

    lateinit var onBookmarkClickListener: OnMovieClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, dateUseCase)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], onBookmarkClickListener)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setMovies(movie: List<MovieTrending>) {
        movies.clear()
        movies.addAll(movie)
        notifyDataSetChanged()
    }
}