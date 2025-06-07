package ru.mareanexx.network.utils.data

data class WrappedResponse<T>(
    val message: String? = null,
    val data: T? = null
)