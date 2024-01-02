package com.qasimnawaz019.data.utils

import android.util.Log
import com.qasimnawaz019.domain.utils.HttpExceptions
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val TIME_OUT = 60_000L

val ktorHttpClient = HttpClient(CIO) {

    install(WebSockets)

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    engine {
        requestTimeout = TIME_OUT
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.d("Logger Ktor ->", message)
            }
        }
        level = LogLevel.ALL
    }


    install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }

    install(ResponseObserver) {
        onResponse { response ->
            Log.d("Http status:", "${response.status.value}")
        }
    }

    HttpResponseValidator {
        validateResponse { response ->

            if (!response.status.isSuccess()) {
                val failureReason = when (response.status) {
                    HttpStatusCode.Unauthorized -> "Unauthorized request"
                    HttpStatusCode.Forbidden -> "${response.status.value} Missing API key."
                    HttpStatusCode.NotFound -> "Invalid Request"
                    HttpStatusCode.UpgradeRequired -> "Upgrade to VIP"
                    HttpStatusCode.RequestTimeout -> "Network Timeout"
                    in HttpStatusCode.InternalServerError..HttpStatusCode.GatewayTimeout -> "${response.status.value} Server Error"

                    else -> "Network error!"
                }

                throw HttpExceptions(
                    response = response,
                    failureReason = failureReason,
                    cachedResponseText = response.bodyAsText(),
                )
            }
        }
    }
}