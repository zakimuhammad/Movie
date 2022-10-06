package com.zaki.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.zaki.movieapp.data.local.LocalDataSource
import com.zaki.movieapp.data.local.entitiy.AuthEntity
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class SignUpViewModelTest {

    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var localDataSource: LocalDataSource

    private lateinit var viewModel: SignUpViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = SignUpViewModel(localDataSource, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `call insertUser then signUpUiState should GoToSignInActivity`() {
        val authEntity = AuthEntity(userName = "zaki", password = "12345", name = "Zaki Mukhammad")

        coJustRun { localDataSource.insertUser(authEntity) }

        viewModel.insertUser(authEntity)
        assertThat(viewModel.signUpUiState.value).isEqualTo(SignUpUIState.GoToSignInActivity)

        coVerify {
            localDataSource.insertUser(any())
        }
    }
}