package com.appbase.di

import com.appbase.data.repository.AppRepositoryImpl
import com.appbase.domain.usecase.AppUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModule {

    @Binds
    abstract fun provideRepositoryUseCase(
        authUseCaseImpl: AppRepositoryImpl
    ): AppUseCase
}