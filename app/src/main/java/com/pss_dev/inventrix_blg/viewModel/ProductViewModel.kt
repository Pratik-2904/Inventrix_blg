package com.pss_dev.inventrix_blg.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pss_dev.inventrix_blg.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor() : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addProduct(product: Product) {
        _products.value += product
    }

    fun updateProduct(updatedProduct: Product) {
        viewModelScope.launch {
            _products.value = _products.value.map { product ->
                if (product.id == updatedProduct.id) updatedProduct else product
            }
        }
    }
}