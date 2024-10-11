package com.pss_dev.inventrix_blg.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pss_dev.inventrix_blg.data.model.ui.BottomNavItem
import com.pss_dev.inventrix_blg.navigation.safeNavigate
import com.pss_dev.inventrix_blg.screens.NavbarScreen.HomeTopBar
import com.pss_dev.inventrix_blg.viewModel.DashboardViewModel

@Composable
fun CommonScreenLayout(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel(),
    content: @Composable (paddingValues: PaddingValues, businessInfoModalState: MutableState<Boolean>) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedNavItem by viewModel.selectedNavItem.collectAsState()
    val businessInfoModalState = remember { mutableStateOf(false) }

    // Remember the last route to prevent unnecessary recomposition
    val lastRoute = rememberSaveable { mutableStateOf("") }

    // Update selected nav item based on current route
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            val route = backStackEntry.destination.route
            if (route != lastRoute.value) {
                lastRoute.value = route ?: ""
                val newIndex = uiState.navItems.indexOfFirst { it.route == route }
                if (newIndex != -1 && newIndex != selectedNavItem) {
                    viewModel.onNavItemSelected(newIndex)
                }
            }
        }
    }

    // Clean up navigation when the composable is disposed
    DisposableEffect(navController) {
        onDispose {
            // Optional: Clean up any navigation listeners or states if needed
        }
    }

    Scaffold(
        topBar = {
            HomeTopBar(
                uiState = uiState,
                businessInfoModalState = businessInfoModalState
            )
        },
        bottomBar = {
            HomeBottomBar(
                items = uiState.navItems,
                selectedItemIndex = selectedNavItem,
                onItemSelected = viewModel::onNavItemSelected,
                navController = navController
            )
        }
    ) { paddingValues ->
        AnimatedContent(
            targetState = content,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300))
            },
            modifier = Modifier.fillMaxSize(), label = ""
        ) { targetContent ->
            Box(modifier = Modifier.fillMaxSize()) {
                targetContent(paddingValues, businessInfoModalState)
            }
        }
    }
}

@Composable
fun HomeBottomBar(
    items: List<BottomNavItem>,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit,
    navController: NavController
) {
    NavigationBar(
        containerColor = Color(0xFF1C1B1F)
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (selectedItemIndex == index) Color(0xFF3D5AFE) else Color.Gray
                    )
                },
                selected = selectedItemIndex == index,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF3D5AFE),
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color(0xFF1C1B1F)
                ),
                onClick = {
                    if (selectedItemIndex != index) {
                        onItemSelected(index)
                        navController.safeNavigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true

                            // Add animation to the navigation options
//                            anim {
//                                enter = fadeIn(animationSpec = tween(300)),
//                                exit = fadeOut(animationSpec = tween(300)),
//                                popEnter = fadeIn(animationSpec = tween(300)),
//                                popExit = fadeOut(animationSpec = tween(300)),
//                            }
                        }
                    }
                }
            )
        }
    }
}