package com.pss_dev.inventrix_blg.screens.BillScreens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pss_dev.inventrix_blg.data.model.InvoiceProducts
import com.pss_dev.inventrix_blg.viewModel.InvoiceViewModels.HandleProductsVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceAddProduct(viewModel : HandleProductsVM ,navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Product") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },

    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    InvoiceProductCard(context = context ,viewModel ,navController)
                }

            }
        }
    }
}

@Composable
fun InvoiceProductCard(
    context: Context,
    viewModel: HandleProductsVM,
    navController: NavController
) {
    // State variables for product details
    var productOrService by remember { mutableStateOf("Product") }
    var name by remember { mutableStateOf("") }
    var sellingPrice by remember { mutableStateOf("") }
    var purchasePrice by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("") }
    var taxrate by remember { mutableStateOf("") }

    // Colors for light and dark themes
    val backgroundColor = if (isSystemInDarkTheme()) Color(0xFF333333) else Color.White
    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val primaryC = MaterialTheme.colorScheme.primary

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Product Details",
                style = MaterialTheme.typography.titleLarge,
                color = textColor
            )

            // Product or Service selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = productOrService == "Product",
                    onClick = { productOrService = "Product" },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                )
                Text(text = "Product", color = textColor)

                RadioButton(
                    selected = productOrService == "Service",
                    onClick = { productOrService = "Service" },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                )
                Text(text = "Service", color = textColor)
            }

            // Product Name
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Product Name", color = textColor) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    cursorColor = primaryC,
                    focusedLabelColor = primaryC
                )
            )

            // Selling Price
            TextField(
                value = sellingPrice,
                onValueChange = { sellingPrice = it },
                label = { Text("Selling Price", color = textColor) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    cursorColor = primaryC,
                    focusedLabelColor = primaryC
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            TextField(
                value = taxrate,
                onValueChange = { input ->
                    // Allow only valid decimal numbers in the input
                    if (input.toFloatOrNull() != null || input.isEmpty()) {
                        taxrate = input
                    }
                },
                label = { Text("Tax Rate %", color = textColor) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    cursorColor = primaryC,
                    focusedLabelColor = primaryC
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )


            // Purchase Price
            TextField(
                value = purchasePrice,
                onValueChange = { purchasePrice = it },
                label = { Text("Purchase Price", color = textColor) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    cursorColor = primaryC,
                    focusedLabelColor = primaryC
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }

    // Separate card for Units
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 8.dp, end = 8.dp)
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(Modifier.padding(16.dp)) {
            Column {
                // Unit TextField for direct input (no dropdown)
                TextField(
                    value = unit,
                    onValueChange = { unit = it },
                    label = { Text("Unit", color = textColor) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        // Handle keyboard done action if needed
                    }),
                    colors = TextFieldDefaults.colors(
                        cursorColor = primaryC,
                        focusedLabelColor = primaryC
                    )
                )
            }
        }
    }

    // Button to Add Product
    Button(
        onClick = {
            if (name.isEmpty() || sellingPrice.isEmpty() || purchasePrice.isEmpty() || unit.isEmpty() || taxrate.isEmpty()) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val newProduct = InvoiceProducts(
                    ProductOrService = productOrService,
                    name = name,
                    sellingP = sellingPrice.toIntOrNull() ?: 0,
                    PurchaseP = purchasePrice.toIntOrNull() ?: 0,
                    taxrate = taxrate.toDoubleOrNull() ?: 0.0,
                    Unit = unit,
                    quantity = 1
                )
                viewModel.addProduct(newProduct)
                navController.navigate("InvoiceProductList")
            }
        },

        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = "Add Product", fontSize = 18.sp)
    }
}
