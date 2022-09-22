package com.zaki.movieapp.view

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaki.movieapp.MovieApplication
import com.zaki.movieapp.databinding.ActivityMainBinding
import com.zaki.movieapp.viewmodel.MovieViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModel: MovieViewModel
    @Inject lateinit var adapter: MovieAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as MovieApplication).appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        collectState()
    }

    private fun setupAdapter() {
        binding.rvMovie.adapter = adapter
        binding.rvMovie.layoutManager = LinearLayoutManager(this)
    }

    private fun collectState() {
        viewModel.getMovies()

        viewModel.movies.observe(this) {
            adapter.setMovies(it)
        }
    }
}