package com.pss_dev.inventrix_blg.viewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pss_dev.inventrix_blg.data.model.Activity
import com.pss_dev.inventrix_blg.data.model.BusinessInfo
import com.pss_dev.inventrix_blg.data.model.DashboardMetric
import com.pss_dev.inventrix_blg.data.model.DashboardUiState
import com.pss_dev.inventrix_blg.data.model.Feature
import com.pss_dev.inventrix_blg.data.model.Summary
import com.pss_dev.inventrix_blg.data.model.ui.BottomNavItem
import com.pss_dev.inventrix_blg.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState


    init {
        loadDashboardData()
    }

    //    private val _selectedNavItem = MutableStateFlow(0)
//    val selectedNavItem: StateFlow<Int> = _selectedNavItem
//
//    fun onNavItemSelected(index: Int) {
//        viewModelScope.launch {
//            _selectedNavItem.emit(index)
//        }
//    }
    private val _selectedNavItem = MutableStateFlow(0)
    val selectedNavItem: StateFlow<Int> = _selectedNavItem.asStateFlow()

    fun onNavItemSelected(index: Int) {
        viewModelScope.launch {
            if (_selectedNavItem.value != index) {
                _selectedNavItem.emit(index)
            }
        }
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            try {
                // Simulate API call
                val mockBusinessInfo = BusinessInfo(
                    name = "ShivShakti Enterprises",
                    ownerName = "Pratik Sukale",
                    contact = "+91 8767911549",
                    address = "123, Main Street, City - 400001",
                    gstin = "22AAAAA0000A1Z5"
                )

                val mockMetrics = listOf(
                    DashboardMetric("Today's Sales", "₹25,000", "+12%"),
                    DashboardMetric("Pending Invoices", "15", "-3"),
                    DashboardMetric("Low Stock Items", "8", "+2")
                )

                val mockFeatures = listOf(
                    Feature(1, "Invoice", Icons.Default.Receipt),
                    Feature(2, "Purchase", Icons.Default.ShoppingCart),
                    Feature(3, "Quotation", Icons.Default.Description),
                    Feature(4, "Delivery Challan", Icons.Default.LocalShipping),
                    Feature(5, "Credit Note", Icons.Default.NoteAdd),
                    Feature(6, "Purchase Order", Icons.Default.Assignment),
                    Feature(7, "Expenses", Icons.Default.AccountBalance),
                    Feature(8, "Proforma Invoice", Icons.Default.ReceiptLong)
                )
                val navItems = listOf(
                    BottomNavItem(Icons.Default.Home, "Home", Screens.home.route),
                    BottomNavItem(Icons.Default.Description, "Bills", Screens.bills.route),
                    BottomNavItem(Icons.Default.Inventory, "Products", Screens.products.route),
                    BottomNavItem(Icons.Default.People, "Parties", Screens.parties.route),
                    BottomNavItem(Icons.Default.MoreHoriz, "More", Screens.more.route)
                )
                val mockSummary = Summary(
                    period = "Last 30 Days",
                    sales = 100.0,
                    purchases = 50000.0,
                )

                val mockActivities = listOf(
                    Activity(
                        1,
                        "Invoice #1234",
                        "Created for John Doe - ₹5,000",
                        LocalDateTime.now().minusHours(2)
                    ),
                    Activity(
                        2,
                        "Purchase Order #789",
                        "Sent to Supplier XYZ - ₹12,000",
                        LocalDateTime.now().minusHours(5)
                    ),
                    Activity(
                        3,
                        "Quotation #456",
                        "Generated for ABC Corp - ₹8,500",
                        LocalDateTime.now().minusDays(1)
                    )
                )

                _uiState.value = DashboardUiState(
                    businessInfo = mockBusinessInfo,
                    metrics = mockMetrics,
                    features = mockFeatures,
                    recentActivities = mockActivities,
                    navItems = navItems,
                    summary = mockSummary,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}
