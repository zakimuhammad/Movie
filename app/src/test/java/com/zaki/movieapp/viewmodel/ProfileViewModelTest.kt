package com.zaki.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.zaki.movieapp.data.local.LocalDataSource
import com.zaki.movieapp.data.local.entitiy.AuthEntity
import com.zaki.movieapp.domain.SessionUseCase
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class ProfileViewModelTest {

    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var localDataSource: LocalDataSource

    @MockK
    lateinit var sessionUseCase: SessionUseCase

    private lateinit var viewModel: ProfileViewModel

    private var testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = ProfileViewModel(localDataSource, sessionUseCase)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `do nothing then userUiState is initial`() {
        assertThat(viewModel.userUiState.value).isEqualTo(ProfileUiState.Initial)
    }

    @Test
    fun `call function getUser with user login data not null then userUiState value should ShowUser`() {
        val authEntity = AuthEntity(
            userName = "zaki",
            password = "12345",
            name = "Zaki"
        )
        every { sessionUseCase.getLoginSession() } returns flowOf("zaki")
        every { localDataSource.getUserLogin(any()) } returns authEntity

        viewModel.getUser()

        assertThat(viewModel.userUiState.value).isEqualTo(ProfileUiState.Initial)
        assertThat(viewModel.userUiState.value).isEqualTo(ProfileUiState.ShowUser(authEntity))
    }

    @Test
    fun `call function logout then userUiState value should GoToLoginActivity`() {
        coJustRun { sessionUseCase.saveSession("") }

        viewModel.logout()

        assertThat(viewModel.userUiState.value).isEqualTo(ProfileUiState.Initial)
        assertThat(viewModel.userUiState.value).isEqualTo(ProfileUiState.GoToLoginActivity)
    }
}