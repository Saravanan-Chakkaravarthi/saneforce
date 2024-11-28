package com.appbase.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.appbase.R
import com.appbase.databinding.ActivityMainBinding
import com.appbase.domain.model.Product
import com.appbase.domain.model.SaveProductRequest
import com.appbase.domain.model.SaveRequestData
import com.appbase.utils.DataState
import com.appbase.utils.ProductListener
import com.appbase.utils.showSnackBar
import com.appbase.utils.showToast
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ProductListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpProductRecyclerView()
        setUpObservers()
        setUpOnClickListener()
    }

    private fun setUpOnClickListener() {
        binding.btnSave.setOnClickListener {
            viewModel.saveProductList(viewModel.saveProductRequest)
        }
    }

    private fun setUpProductRecyclerView() {
        productAdapter = ProductAdapter(this)
        binding.rvProductList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = productAdapter
        }
    }

    private fun setUpObservers() {
        viewModel.productListResponseData.observe(this@MainActivity) { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    showProgressBar(dataState.loading)
                }

                is DataState.Success -> {
                    showProgressBar(false)
                    dataState.data.let { products ->
                        productAdapter.updateData(products)
                    }
                }

                is DataState.Error -> {
                    showProgressBar(false)
                    showToast(dataState.errorMessage)
                }
            }
        }
        viewModel.saveProductListResponse.observe(this@MainActivity) { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    showProgressBar(dataState.loading)
                }

                is DataState.Success -> {
                    showProgressBar(false)
                    if (dataState.data.success) {
                        showToast("Products are saved successfully.")
                    } else {
                        showToast("Unable to save the products.")
                    }
                }

                is DataState.Error -> {
                    showProgressBar(false)
                    showToast(dataState.errorMessage)
                }
            }
        }
    }

    private fun showProgressBar(isProgressBarVisible: Boolean) {
        if (isProgressBarVisible) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun getProductItem(product: List<Product>) {
        viewModel.saveProductRequest.data.clear()
        product.forEach {
            viewModel.saveProductRequest.data.add(
                SaveRequestData(
                    productCode = it.productCode,
                    productName = it.productName,
                    productQty = it.convQty.toInt(),
                    rate = it.price?.toLong() ?: 0,
                    productAmount = it.total?.toLong() ?: 0

                )
            )
        }
    }
}
