package ru.mareanexx.travelogue.data.common

data class WrappedResponse<T>(
    val code: Int? = null,
    val message: String? = null,
    val data: T? = null
)