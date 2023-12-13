package com.qasimnawaz019.data.utils

import com.qasimnawaz019.domain.utils.ConnectivityException
import com.qasimnawaz019.domain.utils.HttpExceptions
import com.qasimnawaz019.domain.utils.NetworkCall
import com.qasimnawaz019.domain.utils.NetworkConnectivity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> HttpClient.safeRequest(
    networkConnectivity: NetworkConnectivity,
    block: HttpRequestBuilder.() -> Unit,
): NetworkCall<T> {
    return try {
        if (networkConnectivity.isConnected()) {
            val response = request { block() }
            NetworkCall.Success(data = response.body())
        } else {
            NetworkCall.Error.NoNetwork(ConnectivityException())
        }
    } catch (exception: ClientRequestException) {
        NetworkCall.Error.HttpError(
            code = exception.response.status.value,
//            errorMessage = "Status Code: ${exception.response.status.value} - API Key Missing",
            errorMessage = exception.message
        )
    } catch (exception: HttpExceptions) {
        NetworkCall.Error.HttpError(
            code = exception.response.status.value,
            errorMessage = exception.message,
        )
    } catch (exception: SerializationException) {
        NetworkCall.Error.SerializationError(
            errorMessage = "Something went wrong",
        )
    } catch (exception: Exception) {
        NetworkCall.Error.GenericError(
//            errorMessage = "Something went wrong",
            errorMessage = "${exception.message}",
        )
    }
}