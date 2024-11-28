package com.appbase.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appbase.domain.model.Product
import com.appbase.domain.model.SaveProduct
import com.appbase.domain.model.SaveProductRequest
import com.appbase.domain.usecase.AppUseCase
import com.appbase.utils.DataState
import com.appbase.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appUseCase: AppUseCase
): ViewModel() {

    init {
        getProductList()
    }

    val saveProductRequest = SaveProductRequest(arrayListOf())

    private val _productListResponseData: MutableLiveData<DataState<ArrayList<Product>>> = MutableLiveData()
    val productListResponseData: LiveData<DataState<ArrayList<Product>>> = _productListResponseData

    private val _saveProductListResponse: MutableLiveData<DataState<SaveProduct>> = MutableLiveData()
    val saveProductListResponse: LiveData<DataState<SaveProduct>> = _saveProductListResponse

    private fun getProductList() {
        viewModelScope.launch (Dispatchers.IO){
            appUseCase.getProductList().onEach {
                _productListResponseData.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun saveProductList(product: SaveProductRequest) {
        viewModelScope.launch (Dispatchers.IO){
            appUseCase.saveProductList(product).onEach {
                _saveProductListResponse.value = it
            }.launchIn(viewModelScope)
        }
    }
}