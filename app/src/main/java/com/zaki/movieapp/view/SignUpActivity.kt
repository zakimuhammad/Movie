package com.zaki.movieapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zaki.movieapp.databinding.ActivitySignUpBinding
import com.zaki.movieapp.viewmodel.SignUpViewModel
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {

    lateinit var  binding: ActivitySignUpBinding

    @Inject lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.buttonLogin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(intent)
        }
    }
}