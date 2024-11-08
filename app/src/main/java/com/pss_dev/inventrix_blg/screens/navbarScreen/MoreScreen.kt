package com.pss_dev.inventrix_blg.screens.navbarScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pss_dev.inventrix_blg.screens.CommonScreenLayout
import com.pss_dev.inventrix_blg.viewModel.DashboardViewModel

@Composable
fun MoreScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    CommonScreenLayout(
        navController = navController,
        viewModel = viewModel
    ) { paddingValues, modalBottomSheetState ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1C1B1F))
        ) {
            Text("More", color = Color.White)
            // Add your more screen content here
        }
    }
}