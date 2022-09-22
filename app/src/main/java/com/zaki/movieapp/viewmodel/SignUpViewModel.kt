package com.zaki.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaki.movieapp.data.local.LocalDataSource
import com.zaki.movieapp.data.local.entitiy.AuthEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val localDataSource: LocalDataSource
): ViewModel() {

    private val _signUpUiState: MutableStateFlow<SignUpUIState> = MutableStateFlow(SignUpUIState.Initial)
    val signUpUiState = _signUpUiState.asStateFlow()

    fun insertUser(authEntity: AuthEntity) = viewModelScope.launch(Dispatchers.IO) {
        _signUpUiState.emit(SignUpUIState.Loading)
        localDataSource.insertUser(authEntity)
        _signUpUiState.emit(SignUpUIState.GoToSignInActivity)
    }
}

sealed class SignUpUIState {
    object Initial: SignUpUIState()
    object Loading: SignUpUIState()
    object GoToSignInActivity: SignUpUIState()
}