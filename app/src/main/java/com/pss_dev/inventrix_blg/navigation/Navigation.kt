package com.pss_dev.inventrix_blg.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stockinvoice.screens.signInScreen.SignInScreen
import com.pss_dev.inventrix_blg.screens.NavbarScreen.BillScreen
import com.pss_dev.inventrix_blg.screens.NavbarScreen.HomeScreen
import com.pss_dev.inventrix_blg.screens.NavbarScreen.MoreScreen
import com.pss_dev.inventrix_blg.screens.NavbarScreen.PartiesScreen
import com.pss_dev.inventrix_blg.screens.NavbarScreen.ProductsScreen
import com.pss_dev.inventrix_blg.screens.SplashScreen
import com.pss_dev.inventrix_blg.screens.auth.SignUpScreen
import com.pss_dev.inventrix_blg.viewModel.AuthViewModel

@Composable
fun Navigation(navController: NavHostController, authviewmodel: AuthViewModel = hiltViewModel()) {
    val startDestination = Screens.splash.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screens.splash.route,
            exitTransition = {
                slideOutHorizontally() + fadeOut()
            }) {
            SplashScreen(navController, authviewmodel)
        }
        composable(route = Screens.home.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300))
            }) {
            HomeScreen(navController = navController, authViewModel = authviewmodel)
        }
        composable(route = Screens.bills.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            BillScreen(navController = navController)
        }
        composable(route = Screens.products.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300))
            }) {
            ProductsScreen(navController)
        }

        composable(route = Screens.parties.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300))
            }) {
            PartiesScreen(navController)
        }
        composable(route = Screens.more.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300))
            }) {
            MoreScreen(navController)
        }
        composable(route = Screens.login.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300))
            }) {
            SignInScreen(navController, authviewmodel)
        }

//        composable("add_product") { AddProductScreen(navController) }

        composable(route = Screens.register.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300))
            }) {
            SignUpScreen(navController, authviewmodel)
        }
        composable(route = Screens.terms.route) {
            //Todo Add Terms Screen
        }
    }

}

fun NavController.safeNavigate(
    route: String,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
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