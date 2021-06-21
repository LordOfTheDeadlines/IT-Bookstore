package com.abramchuk.itbookstore.utils

class Result<T>(val data: T?, val isLoading: Boolean, val error: String?) {

    companion object {
        const val API_ERROR = "api_error"
        const val UNKNOWN_ERROR = "un_er"

        fun <T> success(data: T?): Result<T> = Result(data, false, null)
        fun <T> error(data: T?, error: String): Result<T> = Result(data, false, error)
        fun <T> loading(isLoading: Boolean): Result<T> = Result(null, isLoading, null)
    }
}
