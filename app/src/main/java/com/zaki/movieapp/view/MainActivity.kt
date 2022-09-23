package com.zaki.movieapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zaki.movieapp.MovieApplication
import com.zaki.movieapp.R
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

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavMain.setupWithNavController(navController)
    }

    private fun collectState() {
        viewModel.getMovies()

        viewModel.movies.observe(this) {
            adapter.setMovies(it)
        }
    }
}