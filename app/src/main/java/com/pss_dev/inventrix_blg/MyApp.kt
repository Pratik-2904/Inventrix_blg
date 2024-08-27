package com.pss_dev.inventrix_blg

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.pss_dev.inventrix_blg.navigation.Navigation
import com.pss_dev.inventrix_blg.viewModel.AuthViewModel

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = AuthViewModel() // Or get it via DI
    Navigation(navController = navController)
}
