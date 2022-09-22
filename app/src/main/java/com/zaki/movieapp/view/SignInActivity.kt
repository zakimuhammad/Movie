package com.zaki.movieapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ContentInfoCompat.Flags
import com.zaki.movieapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListener()
    }

    private fun initClickListener() {
        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(intent)
        }
    }
}