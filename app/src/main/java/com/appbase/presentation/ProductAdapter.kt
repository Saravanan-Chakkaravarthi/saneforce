package com.appbase.presentation

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appbase.databinding.LayoutProductListBinding
import com.appbase.domain.model.Product
import com.appbase.utils.ProductListener
import com.google.android.material.textview.MaterialTextView

class ProductAdapter(
    private val productListener: ProductListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val productList = mutableListOf<Product>()
    fun updateData(newData: List<Product>) {
        productList.clear()
        productList.addAll(newData)
        notifyDataSetChanged()
    }


    inner class ProductViewHolder(val binding: LayoutProductListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                tvProductName.text = product.productName
                etQuantity.setText(product.convQty)
                etRate.setText(product.price)
                tvTotalValue.text = "0"

                updateTotalValue(
                    etRate.text.toString().toIntOrNull() ?: 0,
                    etQuantity.text.toString().toIntOrNull() ?: 0,
                    tvTotalValue
                )
                product.total = tvTotalValue.text.toString()

                etRate.removeTextChangedListener(etRate.tag as? TextWatcher)
                val rateWatcher = object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        product.price = if (!s.isNullOrEmpty()) s.toString() else "0"
                        val quantity = etQuantity.text.toString().toIntOrNull() ?: 0
                        updateTotalValue(product.price?.toInt() ?: 0, quantity, tvTotalValue)
                        product.total = tvTotalValue.text.toString()
                        productListener.getProductItem(productList)
                    }

                    override fun afterTextChanged(s: Editable?) {}
                }
                etRate.addTextChangedListener(rateWatcher)
                etRate.tag = rateWatcher

                // Handle Add button click
                btnAdd.setOnClickListener {
                    product.price =
                        if (!etRate.text.isNullOrEmpty()) etRate.text.toString() else "0"
                    val currentQty = product.convQty.toIntOrNull() ?: 0

                    product.convQty = (currentQty + 1).toString()
                    etQuantity.setText(product.convQty)
                    updateTotalValue(
                        product.price?.toInt() ?: 0,
                        product.convQty.toInt(),
                        tvTotalValue
                    )
                    product.total = tvTotalValue.text.toString()
                    productListener.getProductItem(productList)
                }

                // Handle Minus button click
                btnMinus.setOnClickListener {
                    product.price =
                        if (!etRate.text.isNullOrEmpty()) etRate.text.toString() else "0"
                    val currentQty = product.convQty.toIntOrNull() ?: 1

                    if (currentQty > 1) {
                        product.convQty = (currentQty - 1).toString()
                        etQuantity.setText(product.convQty)
                        updateTotalValue(
                            product.price?.toInt() ?: 0,
                            product.convQty.toInt(),
                            tvTotalValue
                        )
                        product.total = tvTotalValue.text.toString()
                    }
                    productListener.getProductItem(productList)
                }

                // Handle Delete button click
                btnDelete.setOnClickListener {
                    productList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    notifyItemRangeChanged(adapterPosition, productList.size)
                    productListener.getProductItem(productList)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutProductListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    private fun updateTotalValue(rate: Int, quantity: Int, tvTotalValue: MaterialTextView) {
        tvTotalValue.text = "${rate * quantity}"
    }
}
