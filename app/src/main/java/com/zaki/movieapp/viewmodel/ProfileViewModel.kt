package com.zaki.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaki.movieapp.data.local.LocalDataSource
import com.zaki.movieapp.data.local.entitiy.AuthEntity
import com.zaki.movieapp.domain.SessionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val sessionUseCase: SessionUseCase
): ViewModel() {

    private val _userUiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.Initial)
    val userUiState = _userUiState.asStateFlow()

    fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        _userUiState.emit(ProfileUiState.Loading)
        val username = sessionUseCase.getLoginSession().first()

        localDataSource.getUserLogin(username)?.let {
            _userUiState.emit(ProfileUiState.ShowUser(it))
        }
    }

    fun logout() = viewModelScope.launch {
        sessionUseCase.saveSession("")
        _userUiState.emit(ProfileUiState.GoToLoginActivity)
    }
}

sealed class ProfileUiState {
    object Initial: ProfileUiState()
    object Loading: ProfileUiState()
    data class ShowUser(val authEntity: AuthEntity): ProfileUiState()
    object GoToLoginActivity: ProfileUiState()
}