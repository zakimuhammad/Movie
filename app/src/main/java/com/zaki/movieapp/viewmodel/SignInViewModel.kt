package com.zaki.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaki.movieapp.data.local.LocalDataSource
import com.zaki.movieapp.domain.SessionUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val sessionUseCase: SessionUseCase,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _signInUiState: MutableStateFlow<SignInUiState> = MutableStateFlow(SignInUiState.Initial)
    val signInUiState = _signInUiState.asStateFlow()

    fun checkLogin(userName: String, password: String) = viewModelScope.launch(ioDispatcher) {
        _signInUiState.emit(SignInUiState.Loading)
        val user = localDataSource.getUserLogin(userName)

        if (user != null) {
            if (user.password == password) {
                _signInUiState.emit(SignInUiState.GoToMainActivity)
                sessionUseCase.saveSession(userName)
            } else {
                _signInUiState.emit(SignInUiState.Failed("Password Salah!"))
            }
        } else {
            _signInUiState.emit(SignInUiState.Failed("User Tidak Ada!"))
        }
    }

    fun checkSession() = viewModelScope.launch(ioDispatcher) {
        sessionUseCase.getLoginSession()
            .collect {
                if (it.isNotEmpty()) {
                    _signInUiState.emit(SignInUiState.GoToMainActivity)
                }
            }
    }
}

sealed class SignInUiState {
    object Initial: SignInUiState()
    object Loading: SignInUiState()
    object GoToMainActivity: SignInUiState()
    data class Failed(val message: String): SignInUiState()
}