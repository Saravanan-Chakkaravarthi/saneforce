package com.appbase.data.repository

import com.appbase.data.remote.ApiService
import com.appbase.domain.usecase.AppUseCase
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appService: ApiService
): AppUseCase {
}