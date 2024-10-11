package com.pss_dev.inventrix_blg.data.model

import java.time.LocalDateTime

data class Activity(
    val id: Int,
    val title: String,
    val description: String,
    val timestamp: LocalDateTime
)