package com.zaki.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.zaki.movieapp.data.local.LocalDataSource
import com.zaki.movieapp.data.local.entitiy.AuthEntity
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coJustRun
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SignUpViewModelTest {

    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var localDataSource: LocalDataSource

    private lateinit var viewModel: SignUpViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = SignUpViewModel(localDataSource)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `call insertUser then signUpUiState should GoToSignInActivity`() {
        val authEntity = AuthEntity(userName = "zaki", password = "12345", name = "Zaki Mukhammad")

        coJustRun { localDataSource.insertUser(authEntity) }

        viewModel.insertUser(authEntity)
        assertThat(viewModel.signUpUiState.value).isEqualTo(SignUpUIState.Initial)
        assertThat(viewModel.signUpUiState.value).isEqualTo(SignUpUIState.GoToSignInActivity)
    }
}