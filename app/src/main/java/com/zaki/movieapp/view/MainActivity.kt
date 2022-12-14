package com.zaki.movieapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.zaki.movieapp.R
import com.zaki.movieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setupNavigation()
  }

  private fun setupNavigation() {
    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.fragment_container_view_main) as NavHostFragment
    val navController = navHostFragment.navController
    binding.bottomNavMain.setupWithNavController(navController)
  }
}