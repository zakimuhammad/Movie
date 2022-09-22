package com.zaki.movieapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ContentInfoCompat.Flags
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.zaki.movieapp.MovieApplication
import com.zaki.movieapp.databinding.ActivitySignInBinding
import com.zaki.movieapp.viewmodel.SignInUiState
import com.zaki.movieapp.viewmodel.SignInViewModel
import javax.inject.Inject

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding

    @Inject lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as MovieApplication).appComponent.inject(this)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListener()
        collectState()
    }

    private fun initClickListener() {
        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener {
            val username = binding.outlineTextUsername.editText?.text.toString()
            val password = binding.outlineTextPassword.editText?.text.toString()
            viewModel.checkLogin(username, password)
        }
    }

    private fun collectState() {
        lifecycleScope.launchWhenStarted {
            viewModel.signInUiState.collect { signInUiState ->
                when(signInUiState) {
                    is SignInUiState.Failed -> {
                        Toast.makeText(this@SignInActivity, signInUiState.message, Toast.LENGTH_SHORT).show()
                        binding.progressBar.isVisible = false
                    }
                    SignInUiState.Initial -> {
                        binding.progressBar.isVisible = false
                    }
                    SignInUiState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    SignInUiState.Success -> {
                        binding.progressBar.isVisible = false
                        val intent = Intent(this@SignInActivity, MainActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}