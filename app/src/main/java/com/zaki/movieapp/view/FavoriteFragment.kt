package com.zaki.movieapp.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaki.movieapp.MovieApplication
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.databinding.FragmentFavoriteBinding
import com.zaki.movieapp.helper.OnMovieClickListener
import com.zaki.movieapp.viewmodel.FavoriteViewModel
import javax.inject.Inject

class FavoriteFragment: Fragment() {

    @Inject
    lateinit var viewModel: FavoriteViewModel
    @Inject
    lateinit var movieAdapter: MovieAdapter

    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.applicationContext as MovieApplication).appComponent.inject(this)

        setupAdapter()
        observeViewModel()
    }

    private fun setupAdapter() {
        binding.rvMovie.apply {
            adapter = this@FavoriteFragment.movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        movieAdapter.onClickListener = object : OnMovieClickListener {
            override fun onClickItem(movieTrending: MovieTrending) {
                val intent = Intent(requireActivity(), DetailMovieActivity::class.java)
                    .putExtra(DetailMovieActivity.MOVIE_EXTRA, movieTrending)

                startActivity(intent)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.getFavoriteMovies()

        viewModel.movies.observe(viewLifecycleOwner) {
            movieAdapter.setMovies(it)
        }
    }
}