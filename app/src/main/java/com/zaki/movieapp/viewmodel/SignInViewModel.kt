package com.zaki.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaki.movieapp.data.local.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val localDataSource: LocalDataSource
): ViewModel() {

    private val _signInUiState: MutableStateFlow<SignInUiState> = MutableStateFlow(SignInUiState.Initial)
    val signInUiState = _signInUiState.asStateFlow()

    fun checkLogin(userName: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        _signInUiState.emit(SignInUiState.Loading)
        val user = localDataSource.getUserLogin(userName)

        if (user != null) {
            if (user.password == password) {
                _signInUiState.emit(SignInUiState.Success)
            } else {
                _signInUiState.emit(SignInUiState.Failed("Password Salah!"))
            }
        } else {
            _signInUiState.emit(SignInUiState.Failed("User Tidak Ada!"))
        }
    }
}

sealed class SignInUiState {
    object Initial: SignInUiState()
    object Loading: SignInUiState()
    object Success: SignInUiState()
    data class Failed(val message: String): SignInUiState()
}