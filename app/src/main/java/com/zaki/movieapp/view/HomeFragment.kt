package com.zaki.movieapp.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaki.movieapp.MovieApplication
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.databinding.FragmentHomeBinding
import com.zaki.movieapp.helper.OnMovieClickListener
import com.zaki.movieapp.viewmodel.HomeUiState
import com.zaki.movieapp.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeFragment : Fragment() {

  @Inject lateinit var viewModel: HomeViewModel

  @Inject lateinit var movieAdapter: MovieAdapter

  private lateinit var binding: FragmentHomeBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = FragmentHomeBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    (activity?.applicationContext as MovieApplication).appComponent.mainComponent().create()
      .inject(this)

    setupAdapter()
    observeViewModel()
  }

  private fun setupAdapter() {
    binding.rvMovie.apply {
      adapter = this@HomeFragment.movieAdapter
      layoutManager = LinearLayoutManager(requireContext())
    }

    movieAdapter.onClickListener = object : OnMovieClickListener {
      override fun onClickItem(movieTrending: MovieTrending) {
        val intent = Intent(
          requireActivity(),
          DetailMovieActivity::class.java
        ).putExtra(DetailMovieActivity.MOVIE_EXTRA, movieTrending)

        startActivity(intent)
      }
    }
  }

  private fun observeViewModel() {
    viewModel.getMovies()

    viewModel.homeUiState.observe(viewLifecycleOwner) {
      when (it) {
        is HomeUiState.Error -> {
          binding.progressBar.isGone = true
          Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        }
        HomeUiState.Initial -> binding.progressBar.isGone = true
        HomeUiState.Loading -> binding.progressBar.isVisible = true
        is HomeUiState.ShowMovies -> {
          movieAdapter.setMovies(it.movies)
          binding.progressBar.isGone = true
        }
      }
    }
  }
}