package com.qasimnawaz019.domain.utils

sealed class NetworkCall<out T : Any?> {

    /**
     * Represents successful network responses (2xx).
     */
    data class Success<out T : Any?>(val data: T) :
        NetworkCall<T>()

    sealed class Error(val message: String?) :
        NetworkCall<Nothing>() {

        /**
         * Represents server errors.
         * @param cause Connectivity lost exception
         */
        data class NoNetwork(
            val cause: Exception
        ) : Error(message = cause.message)

        /**
         * Represents server errors.
         * @param code HTTP Status code
         * @param message Detail exception message
         * @param errorMessage Custom error message
         */
        data class HttpError(
            val code: Int,
            val errorMessage: String?,
        ) : Error(message = errorMessage)

        /**
         * Represent SerializationExceptions.
         * @param message Detail exception message
         * @param errorMessage Formatted error message
         */
        data class SerializationError(
            val errorMessage: String?,
        ) : Error(message = errorMessage)

        /**
         * Represent other exceptions.
         * @param message Detail exception message
         * @param errorMessage Formatted error message
         */
        data class GenericError(
            val errorMessage: String?,
        ) : Error(message = errorMessage)
    }
}