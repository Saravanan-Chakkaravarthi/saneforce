package com.appbase.domain.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("product_code") val productCode: String,
    @SerializedName("product_name") val productName: String,
    @SerializedName("product_unit") val productUnit: String,
    @SerializedName("convQty") var convQty: String,
    var price: String? = "0",
    var total: String? = "0"
)
