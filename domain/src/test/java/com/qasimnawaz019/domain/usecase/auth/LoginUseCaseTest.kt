package com.qasimnawaz019.domain.usecase.auth

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.User
import com.qasimnawaz019.domain.repository.AuthRepo
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginUseCaseTest {

    @Mock
    lateinit var authRepo: AuthRepo

    private lateinit var loginUseCase: LoginUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        loginUseCase = LoginUseCase(authRepo, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cancel()
    }

    @Test
    fun `loginUseCase emits Success on successful login`() = runTest {
        val user = Mockito.mock(User::class.java)
        val loginRequestDto = Mockito.mock(LoginRequestDto::class.java)
        val networkCall =
            NetworkCall.Success(BaseResponse(true, user, "User successfully logged in"))

        `when`(authRepo.login(loginRequestDto)).thenReturn(flowOf(networkCall))

        val params = LoginUseCase.Params(loginRequestDto)

        val resultFlow = loginUseCase.execute(params)

        val result = resultFlow.test {
            assertThat(networkCall).isEqualTo(awaitItem())
            awaitComplete()
        }
        assertThat(result).isNotNull()
    }

    @Test
    fun `loginUseCase emits Error on failed login`() = runTest {
        val loginRequestDto = Mockito.mock(LoginRequestDto::class.java)
        val networkCall =
            NetworkCall.Success(BaseResponse<User>(false, null, "Invalid email or password"))

        `when`(authRepo.login(loginRequestDto)).thenReturn(flowOf(networkCall))

        val params = LoginUseCase.Params(loginRequestDto)

        val resultFlow = loginUseCase.execute(params)

        val result = resultFlow.test {
            assertThat(networkCall).isEqualTo(awaitItem())
            awaitComplete()
        }
        assertThat(result).isNotNull()
    }
}