package com.qasimnawaz019.domain.usecase.cart

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.UserCartRepo
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
class GetUserCartUseCaseTest {

    @Mock
    lateinit var userCartRepo: UserCartRepo

    private lateinit var getUserCartUseCase: GetUserCartUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        getUserCartUseCase = GetUserCartUseCase(userCartRepo, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cancel()
    }

    @Test
    fun `getUserCartUseCase returns Success with user cart data`() = runTest {
        val product = Mockito.mock(Product::class.java)
        val networkCall =
            NetworkCall.Success(
                BaseResponse(
                    true,
                    listOf(product),
                    "Success"
                )
            )

        Mockito.`when`(userCartRepo.getUserCart()).thenReturn(flowOf(networkCall))

        val resultFlow = getUserCartUseCase.execute(Unit)

        val result = resultFlow.test {
            Truth.assertThat(networkCall).isEqualTo(awaitItem())
            awaitComplete()
        }
        Truth.assertThat(result).isNotNull()
    }
}