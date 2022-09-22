package com.zaki.movieapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zaki.movieapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}