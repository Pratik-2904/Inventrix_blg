package com.pss_dev.inventrix_blg.data.model

import com.pss_dev.inventrix_blg.data.model.ui.BottomNavItem

data class DashboardUiState(
    val businessInfo: BusinessInfo = BusinessInfo(
        name = "Loading...",
        ownerName = "",
        contact = "",
        address = "",
        gstin = "",
    ),
    val metrics: List<DashboardMetric> = emptyList(),
    val features: List<Feature> = emptyList(),
    val navItems: List<BottomNavItem> = emptyList(),
    val recentActivities: List<Activity> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val summary: Summary? = null
)

data class Summary(
    val period: String,
    val sales: Double,
    val purchases: Double

)