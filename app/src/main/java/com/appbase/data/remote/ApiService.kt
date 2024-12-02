package com.appbase.data.remote

import com.appbase.domain.model.Product
import com.appbase.domain.model.SaveProduct
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @GET(value = "native_Db_V13.php?axn=get/taskproducts&divisionCode=258")
    suspend fun getProductList(): Response<ArrayList<Product>>

    @Multipart
    @POST(value = "native_Db_V13.php?axn=save/taskproddets&divisionCode=258")
    suspend fun saveProductList(
        @Part("data") data: RequestBody
    ): Response<SaveProduct>
}