package com.qasimnawaz019.domain.usecase.auth

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.qasimnawaz019.domain.dto.login.RegisterRequestDto
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
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterUseCaseTest {

    @Mock
    lateinit var authRepo: AuthRepo

    private lateinit var registerUseCase: RegisterUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        registerUseCase = RegisterUseCase(authRepo, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cancel()
    }

    @Test
    fun `registerUseCase emits Success on successful register`() = runTest {
        val user = Mockito.mock(User::class.java)
        val registerRequestDto = Mockito.mock(RegisterRequestDto::class.java)
        val networkCall =
            NetworkCall.Success(BaseResponse(true, user, "User successfully registered"))

        Mockito.`when`(authRepo.register(registerRequestDto)).thenReturn(flowOf(networkCall))

        val result = registerUseCase.execute(RegisterUseCase.Params(registerRequestDto)).test {
            Truth.assertThat(networkCall).isEqualTo(awaitItem())
            awaitComplete()
        }

        Truth.assertThat(result).isNotNull()
    }

    @Test
    fun `registerUseCase emits Error on failed register`() = runTest {
        val registerRequestDto = Mockito.mock(RegisterRequestDto::class.java)
        val networkCall =
            NetworkCall.Success(BaseResponse<User>(false, null, "Given email is already registered"))

        Mockito.`when`(authRepo.register(registerRequestDto)).thenReturn(flowOf(networkCall))

        val result = registerUseCase.execute(RegisterUseCase.Params(registerRequestDto)).test {
            Truth.assertThat(networkCall).isEqualTo(awaitItem())
            awaitComplete()
        }

        Truth.assertThat(result).isNotNull()
    }
}