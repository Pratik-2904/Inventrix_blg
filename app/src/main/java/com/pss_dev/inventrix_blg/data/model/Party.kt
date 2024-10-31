package com.pss_dev.inventrix_blg.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


data class Party(
    val id: String,
    val name: String,
    val contactPerson: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val gstNumber: String? = null
)


@Entity(tableName = "parties")
data class PartyEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val contactPerson: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val gstNumber: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

fun PartyEntity.toDomain() = Party(
    id = id,
    name = name,
    contactPerson = contactPerson,
    phoneNumber = phoneNumber,
    email = email,
    address = address,
    gstNumber = gstNumber
)

fun Party.toEntity() = PartyEntity(
    id = id,
    name = name,
    contactPerson = contactPerson,
    phoneNumber = phoneNumber,
    email = email,
    address = address,
    gstNumber = gstNumber
)