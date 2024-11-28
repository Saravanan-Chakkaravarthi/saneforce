package com.appbase.data.remote

import com.appbase.domain.model.Product
import com.appbase.domain.model.SaveProduct
import com.appbase.domain.model.SaveProductRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET(value = "native_Db_V13.php?axn=get/taskproducts&divisionCode=258")
    suspend fun getProductList(): Response<ArrayList<Product>>

    @POST(value = "native_Db_V13.php?axn=save/taskproddets&divisionCode=258")
    suspend fun saveProductList(@Body product: SaveProductRequest): Response<SaveProduct>
}