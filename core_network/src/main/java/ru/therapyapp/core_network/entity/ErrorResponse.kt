package ru.therapyapp.core_network.entity

import com.google.gson.Gson
import retrofit2.HttpException

data class ErrorResponse(
    val errorText: String
)

fun Throwable.getErrorMessage() : String {
    return try {
        Gson()
            .fromJson(
                (this as? HttpException)?.response()?.errorBody()?.string(),
                ErrorResponse::class.java
            )?.errorText ?: ""
    } catch (e: Exception) {
        "Прозошла ошибка."
    }
}