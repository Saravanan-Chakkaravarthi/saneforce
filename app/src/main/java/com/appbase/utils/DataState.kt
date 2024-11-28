package com.appbase.utils

sealed class DataState<out R> {
    data class Loading<out B>(val loading: Boolean) : DataState<B>()

    data class Success<out T>(val data: T) : DataState<T>()

    data class Error<out E>(val errorMessage: String) : DataState<E>()
}
