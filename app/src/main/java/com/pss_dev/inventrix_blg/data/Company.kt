package com.pss_dev.inventrix_blg.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Companies")
data class Company(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String,
    var address : String,
    var phone : String,
    var email : String
)