package com.zaki.movieapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zaki.movieapp.MovieApplication
import com.zaki.movieapp.databinding.ActivityMainBinding
import com.zaki.movieapp.viewmodel.MovieViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModel: MovieViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as MovieApplication).appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}