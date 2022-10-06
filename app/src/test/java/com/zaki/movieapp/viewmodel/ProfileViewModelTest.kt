package com.zaki.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.zaki.movieapp.data.local.LocalDataSource
import com.zaki.movieapp.data.local.entitiy.AuthEntity
import com.zaki.movieapp.domain.DataStoreUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi class ProfileViewModelTest {

  @get:Rule var testRule: TestRule = InstantTaskExecutorRule()

  @MockK lateinit var localDataSource: LocalDataSource

  @MockK lateinit var sessionUseCase: DataStoreUseCase

  private lateinit var viewModel: ProfileViewModel

  private val testDispatcher = UnconfinedTestDispatcher()

  @Before fun setUp() {
    MockKAnnotations.init(this)
    Dispatchers.setMain(testDispatcher)
    viewModel = ProfileViewModel(localDataSource, sessionUseCase, testDispatcher)
  }

  @After fun tearDown() {
    clearAllMocks()
    Dispatchers.resetMain()
  }

  @Test fun `do nothing then userUiState is initial`() {
    assertThat(viewModel.userUiState.value).isEqualTo(ProfileUiState.Initial)
  }

  @Test
  fun `call function getUser with user login data not null then userUiState value should ShowUser`() {
    val authEntity = AuthEntity(
      userName = "zaki", password = "12345", name = "Zaki"
    )
    every { sessionUseCase.getLoginSession() } returns flowOf("zaki")
    every { localDataSource.getUserLogin(any()) } returns flowOf(authEntity)

    viewModel.getUser()

    assertThat(viewModel.userUiState.value).isEqualTo(ProfileUiState.ShowUser(authEntity))
  }

  @Test fun `call function logout then userUiState value should GoToLoginActivity`() {
    coJustRun { sessionUseCase.saveSession("") }

    viewModel.logout()

    assertThat(viewModel.userUiState.value).isEqualTo(ProfileUiState.GoToLoginActivity)

    coVerify {
      viewModel.logout()
      sessionUseCase.saveSession(any())
    }
  }
}