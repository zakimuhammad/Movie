package com.zaki.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaki.movieapp.data.local.LocalDataSource
import com.zaki.movieapp.data.local.entitiy.AuthEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _signUpUiState: MutableStateFlow<SignUpUIState> = MutableStateFlow(SignUpUIState.Initial)
    val signUpUiState = _signUpUiState.asStateFlow()

    fun insertUser(authEntity: AuthEntity) = viewModelScope.launch(ioDispatcher) {
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