package com.zaki.movieapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.zaki.movieapp.MovieApplication
import com.zaki.movieapp.data.local.entitiy.AuthEntity
import com.zaki.movieapp.databinding.ActivitySignUpBinding
import com.zaki.movieapp.viewmodel.SignUpUIState
import com.zaki.movieapp.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {

    lateinit var  binding: ActivitySignUpBinding

    @Inject lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as MovieApplication).appComponent.inject(this)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListener()
        collectState()
    }

    private fun initClickListener() {
        binding.buttonLogin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(intent)
        }

        binding.buttonRegister.setOnClickListener {
            val name = binding.outlineTextName.editText?.text.toString()
            val username = binding.outlineTextUsername.editText?.text.toString()
            val password = binding.outlineTextPassword.editText?.text.toString()

            if (name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                val authEntity = AuthEntity(
                    name = name,
                    userName = username,
                    password = password
                )
                viewModel.insertUser(authEntity)
            }
        }
    }

    private fun collectState() {
        lifecycleScope.launchWhenStarted {
            viewModel.signUpUiState.collect { signUpUiState ->
                when(signUpUiState) {
                    SignUpUIState.GoToSignInActivity -> {
                        val intent = Intent(this@SignUpActivity, SignInActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        }
                        startActivity(intent)
                    }
                    SignUpUIState.Initial -> {
                        binding.buttonRegister.isEnabled = true
                        binding.progressBar.isVisible = false
                    }
                    SignUpUIState.Loading -> {
                        binding.buttonRegister.isEnabled = false
                        binding.progressBar.isVisible = true
                    }
                }
            }
        }
    }
}