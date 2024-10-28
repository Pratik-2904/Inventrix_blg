package com.pss_dev.inventrix_blg.screens.NavbarScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pss_dev.inventrix_blg.components.AddProductBottomSheet
import com.pss_dev.inventrix_blg.components.ProductDetailsBottomSheet
import com.pss_dev.inventrix_blg.components.ProductItem
import com.pss_dev.inventrix_blg.data.model.Product
import com.pss_dev.inventrix_blg.screens.CommonScreenLayout
import com.pss_dev.inventrix_blg.viewModel.ProductViewModel

@Composable
fun ProductsScreen(
    navController: NavController = rememberNavController(),
    viewModel: ProductViewModel = hiltViewModel()
) {
    val products by viewModel.products.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    //State variables
    var showAddProductSheet by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    CommonScreenLayout(navController) { paddingValues, _ ->
        ProductsContent(
            paddingValues = paddingValues,
            products = products,
            searchQuery = searchQuery,
            onSearchQueryChange = { viewModel.updateSearchQuery(it) },
            onAddClick = { showAddProductSheet = true },
            onProductClick = { product -> selectedProduct = product },
            onProductUpdate = { product -> viewModel.updateProduct(product) },
            showAddProductSheet = showAddProductSheet,
            onDismissAddSheet = { showAddProductSheet = false },
            onSaveNewProduct = { product ->
                viewModel.addProduct(product)
                showAddProductSheet = false
            },
            selectedProduct = selectedProduct,
            onDismissProductDetails = { selectedProduct = null }
        )
    }
}

@Composable
private fun ProductsContent(
    paddingValues: PaddingValues,
    products: List<Product>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    onProductUpdate: (Product) -> Unit,
    showAddProductSheet: Boolean,
    onDismissAddSheet: () -> Unit,
    onSaveNewProduct: (Product) -> Unit,
    selectedProduct: Product?,
    onDismissProductDetails: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF1C1B1F)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF2D2D2D),
                        unfocusedContainerColor = Color(0xFF2D2D2D),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color(0xFF3D5AFE)
                    ),
                    placeholder = { Text("Search products", color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray
                        )
                    }
                )

                // Products List
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(
                        items = products,
                        key = { it.id } // Add this for better performance
                    ) { product ->
                        ProductItem(
                            product = product,
                            onClick = { onProductClick(product) }
                        )
                    }
                }
            }

            // Add Product FAB
            FloatingActionButton(
                onClick = onAddClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = Color(0xFFE91E63)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Product",
                    tint = Color.White
                )
            }

            // Modal Sheets
            if (showAddProductSheet) {
                AddProductBottomSheet(
                    onDismiss = onDismissAddSheet,
                    onSave = onSaveNewProduct
                )
            }

            selectedProduct?.let { product ->
                ProductDetailsBottomSheet(
                    product = product,
                    onDismiss = onDismissProductDetails,
                    onEdit = onProductUpdate
                )
            }
        }
    }
}

// Updated ProductItem.kt
@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        color = Color(0xFF2D2D2D),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "₹${product.price}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFE91E63)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Stock: ${product.stock}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}
