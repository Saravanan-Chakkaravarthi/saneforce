package com.appbase.domain.usecase

import com.appbase.domain.model.Product
import com.appbase.domain.model.SaveProduct
import com.appbase.domain.model.SaveProductRequest
import com.appbase.utils.DataState
import kotlinx.coroutines.flow.Flow

interface AppUseCase {
    suspend fun getProductList(): Flow<DataState<ArrayList<Product>>>
    suspend fun saveProductList(product: SaveProductRequest): Flow<DataState<SaveProduct>>
}