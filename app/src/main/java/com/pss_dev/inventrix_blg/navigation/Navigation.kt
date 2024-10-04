package com.pss_dev.inventrix_blg.navigation

import android.transition.Transition
import android.util.Log
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stockinvoice.screens.signInScreen.SignInScreen
//import com.pss_dev.inventrix_blg.GoogleSignIn.Onetapprofile
import com.pss_dev.inventrix_blg.screens.HomeScreen
import com.pss_dev.inventrix_blg.screens.SignUpScreen
import com.pss_dev.inventrix_blg.screens.SplashScreen
import com.pss_dev.inventrix_blg.viewModel.AuthViewModel

@Composable
fun Navigation(navController: NavHostController, authviewmodel: AuthViewModel = hiltViewModel() ) {
    val startDestination = Screens.splash.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screens.splash.route,
            exitTransition = {
                slideOutHorizontally() + fadeOut()
            }) {
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
            SignUpScreen(navController,authviewmodel)
        }
        composable(route = Screens.terms.route) {
            //Todo Add Terms Screen
        }
        composable(route = Screens.ot1.route) {
//Onetapprofile(navController)
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