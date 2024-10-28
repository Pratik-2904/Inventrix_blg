package com.pss_dev.inventrix_blg.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.pss_dev.inventrix_blg.data.model.Product


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductBottomSheet(
    onDismiss: () -> Unit,
    onSave: (Product) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var isService by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1C1B1F),
        dragHandle = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp),
                    color = Color.Gray
                ) {}
                Text(
                    text = "Add Product/Service",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp)
        ) {
            if (showError) {
                Text(
                    text = "Please fill in all required fields",
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name*", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2D2D2D),
                    unfocusedContainerColor = Color(0xFF2D2D2D),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFF3D5AFE)
                ),
            )

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2D2D2D),
                    unfocusedContainerColor = Color(0xFF2D2D2D),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFF3D5AFE)
                ),
            )

            TextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price*", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2D2D2D),
                    unfocusedContainerColor = Color(0xFF2D2D2D),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFF3D5AFE)
                ),
                prefix = { Text("₹", color = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            TextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock*", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2D2D2D),
                    unfocusedContainerColor = Color(0xFF2D2D2D),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFF3D5AFE)
                ),
                prefix = { Text("Units", color = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Is this a service?",
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = isService,
                    onCheckedChange = { isService = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF3D5AFE),
                        checkedTrackColor = Color(0xFF3D5AFE).copy(alpha = 0.5f)
                    )
                )
            }

            Button(
                onClick = {
                    if (name.isBlank() || price.isBlank()) {
                        showError = true
                    } else {
                        onSave(
                            Product(
                                name = name,
                                description = description,
                                price = price.toDoubleOrNull() ?: 0.0,
                                isService = isService,
                                stock = stock.toIntOrNull() ?: 0,
                                sku = 0.toString()
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3D5AFE)
                )
            ) {
                Text("Save", color = Color.White)
            }
        }
    }
}