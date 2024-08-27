package com.pss_dev.inventrix_blg.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String,
    val isLoggedIn: Boolean = false,
    val name : String? = null,
    val email : String? =  null,
    val mobile : String? = null,
    val companies : List<Company> = emptyList(),
    val userRole : AccessLevel
)

enum class AccessLevel {
    ADMIN,CASHIER,STOCKIST
}
