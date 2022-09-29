package ru.therapyapp.core_network.entity

sealed class RequestResult<out T : Any?> {
    data class Success<out T : Any?>(val data: T) : RequestResult<T>()
    data class Error(val message: String?) : RequestResult<Nothing>()
}

val RequestResult<*>.succeeded
    get() = this is RequestResult.Success
