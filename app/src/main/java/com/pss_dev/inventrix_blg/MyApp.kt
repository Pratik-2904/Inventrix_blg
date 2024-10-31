package com.pss_dev.inventrix_blg

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.pss_dev.inventrix_blg.navigation.Navigation
import com.pss_dev.inventrix_blg.viewModel.AuthViewModel

@Composable
fun MyApp(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    Navigation(navController = navController, authViewModel = authViewModel)
}
