package com.appbase.presentation

import androidx.lifecycle.ViewModel
import com.appbase.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appUseCase: AppUseCase
): ViewModel() {
}