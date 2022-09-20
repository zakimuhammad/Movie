package com.zaki.movieapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zaki.movieapp.MovieApplication
import com.zaki.movieapp.R
import com.zaki.movieapp.viewmodel.MovieViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as MovieApplication).appComponent.inject(this)
        setContentView(R.layout.activity_main)
    }
}