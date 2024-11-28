package com.appbase.utils

import com.appbase.domain.model.Product

interface ProductListener {
    fun getProductItem(product: List<Product>)
}