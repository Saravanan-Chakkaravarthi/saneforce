package com.appbase.utils

import kotlinx.coroutines.flow.FlowCollector

object AppUtils {
    suspend fun <T> FlowCollector<DataState<T>>.handleApiError(code: Int) {
        val errorMessage = when (code) {
            401 -> "Unauthorized access. Please log in again."
            500 -> "Server error. Please try again later."
            else -> "Error code $code: Something went wrong."
        }
        emit(DataState.Error(errorMessage))
    }
}


