package com.appbase.domain.model

import com.google.gson.annotations.SerializedName

data class SaveProductRequest(
    val data: ArrayList<SaveRequestData>
)

data class SaveRequestData(
    @SerializedName("product_code") val productCode: String,
    @SerializedName("product_name") val productName: String,
    @SerializedName("Product_Qty") val productQty: Int,
    @SerializedName("Rate") val rate: Long,
    @SerializedName("Product_Amount") val productAmount: Long,
)
