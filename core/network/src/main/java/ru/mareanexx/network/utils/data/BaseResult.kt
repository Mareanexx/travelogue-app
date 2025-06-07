package ru.mareanexx.network.utils.data

sealed class BaseResult<out T: Any, out E: Any> {
    data class Success<T : Any>(val data: T) : BaseResult<T, Nothing>()
    data class Error<E : Any>(val error: E, val code: Int = 400) : BaseResult<Nothing, E>()
}