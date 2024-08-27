package com.pss_dev.inventrix_blg.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stockinvoice.screens.signInScreen.SignInScreen
import com.pss_dev.inventrix_blg.screens.HomeScreen
import com.pss_dev.inventrix_blg.screens.SplashScreen
import com.pss_dev.inventrix_blg.viewModel.AuthViewModel

@Composable
fun Navigation(navController: NavHostController, authviewmodel: AuthViewModel ) {
    val startDestination = Screens.splash.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screens.splash.route) {
            SplashScreen(navController,authviewmodel)
        }
        composable(route = Screens.home.route) {
            HomeScreen(authviewmodel)
        }
        composable(route = Screens.bills.route) {
            //Todo Add Bills Screen
        }
        composable(route = Screens.products.route) {
            //Todo Add Products Screen
        }
        composable(route = Screens.parties.route) {
            //Todo Add Parties Screen
        }
        composable(route = Screens.more.route) {
            //Todo Add More Screen
        }
        composable(route = Screens.login.route) {
            SignInScreen(navController,authviewmodel)
        }
        composable(route = Screens.register.route) {
            //Todo Add Register Screen
        }
        composable(route = Screens.terms.route) {
            //Todo Add Terms Screen
        }
    }

}

fun NavController.safeNavigate(route: String) {
    val currentRoute = currentDestination?.route
    Log.d("NavController", "Current Route: $currentRoute, Target Route: $route")

    // Navigate only if the current destination is not the same as the target route
    if (currentRoute != route) {
        Log.d("NavController", "Navigating to $route")
        try {
            this.navigate(route)
        } catch (e: Exception) {
            Log.e("NavController", "Navigation failed: ${e.message}", e)
        }
    } else {
        Log.d("NavController", "Already on the $route screen, skipping navigation")
    }
}