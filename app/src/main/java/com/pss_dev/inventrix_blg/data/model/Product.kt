package com.pss_dev.inventrix_blg.data.model

import java.util.UUID

// Data class for Product
data class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val price: Double,
    val isService: Boolean = false,
    val category: String = "Not Defined",

    //TODO confifure the following variables
    val stock: Any,
    val sku: String
) {


}