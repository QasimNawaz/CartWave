package com.qasimnawaz019.domain.utils

sealed class ApiResponse<out T> {

    /**
     * Represents successful network responses (2xx).
     */
    data class Success<T>(val body: T) : ApiResponse<T>()

    sealed class Error : ApiResponse<Nothing>() {

        /**
         * Represents server errors.
         * @param cause Connectivity lost exception
         */
        data class NoNetwork(
            val cause: Exception
        ) : Error()

        /**
         * Represents server errors.
         * @param code HTTP Status code
         * @param message Detail exception message
         * @param errorMessage Custom error message
         */
        data class HttpError(
            val code: Int,
            val message: String?,
            val errorMessage: String?,
        ) : Error()

        /**
         * Represent SerializationExceptions.
         * @param message Detail exception message
         * @param errorMessage Formatted error message
         */
        data class SerializationError(
            val message: String?,
            val errorMessage: String?,
        ) : Error()

        /**
         * Represent other exceptions.
         * @param message Detail exception message
         * @param errorMessage Formatted error message
         */
        data class GenericError(
            val message: String?,
            val errorMessage: String?,
        ) : Error()
    }
}