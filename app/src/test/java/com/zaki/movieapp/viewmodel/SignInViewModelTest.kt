package com.zaki.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.zaki.movieapp.data.local.LocalDataSource
import com.zaki.movieapp.data.local.entitiy.AuthEntity
import com.zaki.movieapp.domain.DataStoreUseCase
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class SignInViewModelTest {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var localDataSource: LocalDataSource

    @MockK
    lateinit var sessionUseCase: DataStoreUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: SignInViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = SignInViewModel(localDataSource, sessionUseCase, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `call checkSession with session then signInUiState value should GoToMainActivity`() {
        every { sessionUseCase.getLoginSession() } returns flowOf("justSession")

        viewModel.checkSession()

        assertThat(viewModel.signInUiState.value).isEqualTo(SignInUiState.GoToMainActivity)
    }

    @Test
    fun `check login with password equals database password then signInUiState should GoToMainActivity`() {
        val authEntity = AuthEntity(
            userName = "zaki",
            password = "12345",
            name = "Zaki Mukhammad"
        )
        every { localDataSource.getUserLogin(any()) } returns authEntity

        coJustRun { sessionUseCase.saveSession(authEntity.userName) }

        viewModel.checkLogin(userName = authEntity.userName, password = authEntity.password)

        assertThat(viewModel.signInUiState.value).isEqualTo(SignInUiState.GoToMainActivity)
    }

    @Test
    fun `check login with username equals database username then signInUiState should failed`() {
        val authEntity = AuthEntity(
            userName = "zaki",
            password = "12345",
            name = "Zaki Mukhammad"
        )
        every { localDataSource.getUserLogin(any()) } returns authEntity

        viewModel.checkLogin(userName = authEntity.userName, password = "123456")

        assertThat(viewModel.signInUiState.value).isEqualTo(SignInUiState.Failed("Password Salah!"))
    }

    @Test
    fun `check login with null user data then signInUiState should failed`() {
        every { localDataSource.getUserLogin(any()) } returns null

        viewModel.checkLogin(userName = "zaki", password = "123456")

        assertThat(viewModel.signInUiState.value).isEqualTo(SignInUiState.Failed("User Tidak Ada!"))
    }
}