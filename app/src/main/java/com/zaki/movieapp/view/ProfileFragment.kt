package com.zaki.movieapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.zaki.movieapp.MovieApplication
import com.zaki.movieapp.databinding.FragmentProfileBinding
import com.zaki.movieapp.viewmodel.ProfileUiState
import com.zaki.movieapp.viewmodel.ProfileViewModel
import javax.inject.Inject

class ProfileFragment : Fragment() {

  private lateinit var binding: FragmentProfileBinding

  @Inject lateinit var viewModel: ProfileViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = FragmentProfileBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    (activity?.applicationContext as MovieApplication).appComponent.mainComponent().create()
      .inject(this)

    observeViewModel()
    initClickListener()
  }

  @SuppressLint("SetTextI18n") private fun observeViewModel() {
    viewModel.getUser()

    lifecycleScope.launchWhenStarted {
      viewModel.userUiState.collect { uiState ->
        when (uiState) {
          ProfileUiState.GoToLoginActivity -> {
            binding.progressBar.isVisible = false
            val intent = Intent(
              requireActivity(),
              SignInActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
          }
          ProfileUiState.Initial -> binding.progressBar.isVisible = false
          ProfileUiState.Loading -> binding.progressBar.isVisible = true
          is ProfileUiState.ShowUser -> {
            binding.progressBar.isVisible = false
            binding.tvProfileName.text = "Hello ${uiState.authEntity.name}"
            binding.tvProfileUsername.text = "@${uiState.authEntity.userName}"
          }
        }
      }
    }
  }

  private fun initClickListener() {
    binding.buttonLogout.setOnClickListener {
      viewModel.logout()
    }
  }
}