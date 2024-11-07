package com.pss_dev.inventrix_blg.viewModel.InvoiceViewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pss_dev.inventrix_blg.data.model.InvoiceProducts
import com.pss_dev.inventrix_blg.data.model.Product
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HandleProductsVM @Inject constructor() : ViewModel() {
    private val _products = mutableStateListOf<InvoiceProducts>()
    val products: SnapshotStateList<InvoiceProducts> get() = _products

    // Function to add a new product or update quantity if it already exists
    fun addProduct(product: InvoiceProducts) {
        viewModelScope.launch {
            val existingProduct = _products.find { it.name == product.name }
            if (existingProduct != null) {
                val index = _products.indexOf(existingProduct)
                _products[index] = existingProduct.copy(quantity = existingProduct.quantity + 1)
            } else {
                _products.add(product.copy(quantity = 1))
            }
        }
    }

    // Function to delete a product
    fun deleteProduct(product: InvoiceProducts) {
        viewModelScope.launch {
            _products.remove(product)
        }
    }

    // Function to update a product's details
    fun updateProduct(updatedProduct: InvoiceProducts) {
        viewModelScope.launch {
            val index = _products.indexOfFirst { it.name == updatedProduct.name }
            if (index != -1) {
                _products[index] = updatedProduct
            }
        }
    }

    // Function to increase quantity
    fun increaseQuantity(product: InvoiceProducts) {
        viewModelScope.launch {
            val index = _products.indexOfFirst { it.name == product.name }
            if (index != -1) {
                val updatedProduct = _products[index].copy(quantity = _products[index].quantity + 1)
                _products[index] = updatedProduct
            }
        }
    }

    // Function to decrease quantity
    fun decreaseQuantity(product: InvoiceProducts) {
        viewModelScope.launch {
            val index = _products.indexOfFirst { it.name == product.name }
            if (index != -1 && _products[index].quantity > 1) { // Ensure quantity doesn't go below 1
                val updatedProduct = _products[index].copy(quantity = _products[index].quantity - 1)
                _products[index] = updatedProduct
            }
        }
    }

    // Function to get the current quantity for a product by name
    fun getQuantity(productName: String): Int {
        return _products.find { it.name == productName }?.quantity ?: 0
    }
}
