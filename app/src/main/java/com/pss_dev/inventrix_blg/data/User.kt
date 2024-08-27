package com.pss_dev.inventrix_blg.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

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
//    val companies : List<Company> = emptyList(),
    val companies : String? = null,
    val userRole : AccessLevel
)

enum class AccessLevel {
    ADMIN,CASHIER,STOCKIST
}
//class Converters {
//    @TypeConverter
//    fun fromCompanyList(companyList: List<Company>?): String? {
//        return companyList?.joinToString(separator = ",") { it.name }
//    }
//
//    @TypeConverter
//    fun toCompanyList(data: String?): List<Company>? {
//        return data?.split(",")?.map { Company(it) }
//    }
//}