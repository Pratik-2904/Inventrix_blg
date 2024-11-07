package com.pss_dev.inventrix_blg.screens.BillScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pss_dev.inventrix_blg.data.model.InvoiceProducts
import com.pss_dev.inventrix_blg.viewModel.InvoiceViewModels.HandleProductsVM
@Composable
fun InvoiceProductList(viewModel: HandleProductsVM, navController: NavController) {
    Column {
        Button(onClick = {
            navController.navigate("InvoiceAddProduct")
        }) {
            Text("Add Product")
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.products) { product ->
                ProductCard(product = product, viewModel = viewModel)
            }
        }
    }
}




@Composable
fun ProductCard(product: InvoiceProducts, viewModel: HandleProductsVM) {
    val quantity = viewModel.getQuantity(product.name)
    var isDialogOpen by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${product.name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Selling Price: ${product.sellingP}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Purchase Price: ${product.PurchaseP}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Unit: ${product.Unit}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Tax Rate: ${product.taxrate}%", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Type: ${product.ProductOrService}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Quantity: $quantity", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            // Row for quantity adjustment
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { viewModel.increaseQuantity(product) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("+")
                }

                Button(
                    onClick = { viewModel.decreaseQuantity(product) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("-")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row for edit and delete actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { isDialogOpen = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Edit")
                }

                Button(
                    onClick = { viewModel.deleteProduct(product) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Delete")
                }
            }
        }
    }

    if (isDialogOpen) {
        EditProductDialog(
            product = product,
            onDismiss = { isDialogOpen = false },
            onSave = { updatedProduct ->
                viewModel.updateProduct(updatedProduct)
                isDialogOpen = false
            }
        )
    }
}



@Composable
fun EditProductDialog(
    product: InvoiceProducts,
    onDismiss: () -> Unit,
    onSave: (InvoiceProducts) -> Unit
) {
    var name by remember { mutableStateOf(product.name) }
    var sellingPrice by remember { mutableStateOf(product.sellingP.toString()) }
    var purchasePrice by remember { mutableStateOf(product.PurchaseP.toString()) }
    var unit by remember { mutableStateOf(product.Unit) }
    var taxRate by remember { mutableStateOf(product.taxrate.toString()) }
    var productType by remember { mutableStateOf(product.ProductOrService) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Product") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Product Name") }
                )
                OutlinedTextField(
                    value = sellingPrice,
                    onValueChange = { sellingPrice = it },
                    label = { Text("Selling Price") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = purchasePrice,
                    onValueChange = { purchasePrice = it },
                    label = { Text("Purchase Price") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = unit,
                    onValueChange = { unit = it },
                    label = { Text("Unit") }
                )
                OutlinedTextField(
                    value = taxRate,
                    onValueChange = { taxRate = it },
                    label = { Text("Tax Rate (%)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = productType,
                    onValueChange = { productType = it },
                    label = { Text("Type") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedProduct = product.copy(
                    name = name,
                    sellingP = sellingPrice.toIntOrNull() ?: product.sellingP,
                    PurchaseP = purchasePrice.toIntOrNull() ?: product.PurchaseP,
                    Unit = unit,
                    taxrate = taxRate.toDoubleOrNull() ?: product.taxrate,
                    ProductOrService = productType
                )
                onSave(updatedProduct)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


