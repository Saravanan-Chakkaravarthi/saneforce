package com.appbase.data.repository

import com.appbase.data.remote.ApiService
import com.appbase.domain.model.Product
import com.appbase.domain.model.SaveProduct
import com.appbase.domain.model.SaveProductRequest
import com.appbase.domain.usecase.AppUseCase
import com.appbase.utils.AppUtils.handleApiError
import com.appbase.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): AppUseCase {
    override suspend fun getProductList(): Flow<DataState<ArrayList<Product>>> = flow {
        emit(DataState.Loading(true))
        try {
            val response = apiService.getProductList()

            if (response.isSuccessful) {
                val productList = response.body()
                if (!productList.isNullOrEmpty()) {
                    emit(DataState.Success(productList))
                } else {
                    emit(DataState.Error("Product list is empty."))
                }
            } else {
                handleApiError(response.code())
            }
        } catch (e: Exception) {
            emit(DataState.Error("An unexpected error occurred: ${e.message}"))
        } finally {
            emit(DataState.Loading(false))
        }
    }

    override suspend fun saveProductList(product: SaveProductRequest): Flow<DataState<SaveProduct>> = flow{
        emit(DataState.Loading(true))
        try {
            val response = apiService.saveProductList(product)

            if (response.isSuccessful) {
                val productResponse = response.body()
                if (productResponse?.success == true) {
                    emit(DataState.Success(productResponse))
                } else {
                    emit(DataState.Error("Unable to save the products. Please try after some time."))
                }
            } else {
                handleApiError(response.code())
            }
        } catch (e: Exception) {
            emit(DataState.Error("An unexpected error occurred: ${e.message}"))
        } finally {
            emit(DataState.Loading(false))
        }
    }
}
