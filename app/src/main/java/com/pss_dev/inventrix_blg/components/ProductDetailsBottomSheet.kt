package com.pss_dev.inventrix_blg.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pss_dev.inventrix_blg.data.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsBottomSheet(
    product: Product,
    onDismiss: () -> Unit,
    onEdit: (Product) -> Unit
) {
    var showEditSheet by remember { mutableStateOf(false) }

    if (showEditSheet) {
        EditProductBottomSheet(
            product = product,
            onDismiss = { showEditSheet = false },
            onSave = { updatedProduct ->
                onEdit(updatedProduct)
                showEditSheet = false
                onDismiss()
            }
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1C1B1F)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Product Details",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                IconButton(
                    onClick = { showEditSheet = true }
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit Product",
                        tint = Color(0xFFE91E63)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            DetailItem("Product Name", product.name)
            DetailItem("Description", product.description)
            DetailItem("Category", product.category)
            DetailItem("Price", "₹${product.price}")
            DetailItem("Stock", product.stock.toString())
            DetailItem("SKU", product.sku)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}