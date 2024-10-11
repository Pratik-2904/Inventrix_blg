//package com.pss_dev.inventrix_blg.screens
//
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowDropDown
//import androidx.compose.material.icons.filled.PlayArrow
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.IconButtonDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.NavigationBarItemDefaults
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.pss_dev.inventrix_blg.data.model.DashboardUiState
//import com.pss_dev.inventrix_blg.data.model.Feature
//import com.pss_dev.inventrix_blg.data.model.ui.BottomNavItem
//import com.pss_dev.inventrix_blg.viewModel.AuthViewModel
//import com.pss_dev.inventrix_blg.viewModel.DashboardViewModel
//
//// Data Classes
//data class SummaryCard(
//    val period: String,
//    val sales: Double,
//    val purchases: Double
//)
//
//data class FeatureItem(
//    val icon: ImageVector,
//    val name: String,
//    val route: String
//)
//
//// UI State
//data class HomeUiState(
//    val companyName: String = "M/S SHIVSHAKTI STA...",
//    val summary: SummaryCard = SummaryCard("This Year", 32690.00, 0.00),
//    val isLoading: Boolean = false
//)
//
//@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
//@Composable
//fun HomeScreen2(
//    viewModel: DashboardViewModel = hiltViewModel(),
//    authviewmodel: AuthViewModel = hiltViewModel()
//) {
//
//    val uiState by viewModel.uiState.collectAsState()
//    val navItems = uiState.navItems
//    val features = uiState.features
//    val businessInfoModalState = remember { mutableStateOf(false) }
//    val selectedItemIndex by viewModel.selectedNavItem.collectAsState() // State from ViewModel
//
//    Scaffold(
//        topBar = { HomeTopBar(uiState, businessInfoModalState) },
//        bottomBar = {
//            HomeBottomBar(
//                items = navItems,
//                selectedItemIndex = selectedItemIndex,
//                onItemSelected = { viewModel.onNavItemSelected(it) } // Use ViewModel to manage state
//            )
//        },
//        content = { paddingValues -> // Pass Scaffold padding directly
//            Surface(
//                modifier = Modifier
//                    .fillMaxSize(),
//                color = Color(0xFF1C1B1F)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(paddingValues),
//                ) {
//
//                    LazyColumn {
//                        item {
//                            // Carousel
//                            HorizontalDisplayAdPage()
//
//
//                            // Summary Card
//                            SummaryCard()
//
//                            // Create Section
//                            HomeCreateSection()
//                        }
//                    }
//
//                    // Features Grid
//                    FeatureGrid(features = features)
//
//
//                }
//            }
//        }
//    )
//}
//
//@Composable
//fun FeatureGrid(features: List<Feature>) {
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(4),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//    ) {
//        items(features, key = { it.id }) { feature -> // Use keys to improve recomposition
//            FeatureItem(feature)
//        }
//    }
//}
//
//@Composable
//private fun FeatureItem(feature: Feature) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.padding(8.dp)
//    ) {
//        Icon(
//            imageVector = feature.icon,
//            contentDescription = feature.name, // Ensure meaningful contentDescription
//            tint = Color(0xFF3D5AFE),
//            modifier = Modifier.size(24.dp)
//        )
//        Text(
//            text = feature.name,
//            color = Color.White,
//            style = MaterialTheme.typography.bodySmall,
//            textAlign = TextAlign.Center
//        )
//    }
//}
//
//
//@Composable
//fun HomeBottomBar(
//    items: List<BottomNavItem>,
//    selectedItemIndex: Int,
//    onItemSelected: (Int) -> Unit
//) {
//    NavigationBar(
//        containerColor = Color(0xFF1C1B1F)
//    ) {
//        items.forEachIndexed { index, item ->
//            NavigationBarItem(
//                icon = { Icon(item.icon, contentDescription = null) },
//                label = { Text(item.label) },
//                selected = selectedItemIndex == index,
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = Color(0xFF3D5AFE),
//                    unselectedIconColor = Color.Gray
//                ),
//                onClick = { onItemSelected(index) }
//            )
//        }
//    }
//}
//
//
//@Composable
//@OptIn(ExperimentalMaterial3Api::class)
//private fun HomeTopBar(uiState: DashboardUiState, businessInfoModalState: MutableState<Boolean>) {
//    TopAppBar(
//        title = {
//            Row(
//                modifier = Modifier.clickable(
//                    onClick = {
//                        // Handle the click event here
//                        businessInfoModalState.value = true
//                    }
//                ),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = uiState.businessInfo.name,
//                    color = Color.White
//                )
//                Icon(
//                    imageVector = Icons.Default.ArrowDropDown,
//                    contentDescription = null,
//                    tint = Color.White
//                )
//            }
//        },
//        actions = {
//            Text(
//                text = "PSS 🇮🇳",
//                color = Color.White,
//                modifier = Modifier.padding(end = 16.dp)
//            )
//        },
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = Color(0xFF1C1B1F)
//        )
//    )
//}
//
//@Composable
//private fun HomeCreateSection() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            "Create",
//            color = Color.White,
//            style = MaterialTheme.typography.titleLarge
//        )
//        IconButton(
//            onClick = { /* TODO */ },
//            colors = IconButtonDefaults.iconButtonColors(
//                containerColor = Color(0xFFE91E63)
//            )
//        ) {
//            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
//        }
//    }
//}
//
//@Composable
//private fun SummaryCard() {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFF2D2D2D)
//        )
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(
//                    //                            uiState.summary.period,
//                    "2000 days",
//                    color = Color.White,
//                    style = MaterialTheme.typography.titleLarge
//                )
//                Icon(
//                    Icons.Default.ArrowDropDown,
//                    contentDescription = null,
//                    tint = Color.White
//                )
//            }
//            Text(
//                "View Bills",
//                color = Color(0xFF3D5AFE),
//                style = MaterialTheme.typography.titleMedium
//            )
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp, vertical = 8.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column {
//                Text("Sales", color = Color.Gray)
//                Text(
//                    //                            "${'uiState.summary.sales'}",
//                    "₹2000",
//                    color = Color.White,
//                    style = MaterialTheme.typography.headlineSmall
//                )
//            }
//            Column {
//                Text("Purchases", color = Color.Gray)
//                Text(
//                    //                            "₹${uiState.summary.purchases}",
//                    "217634187",
//                    color = Color.White,
//                    style = MaterialTheme.typography.headlineSmall
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun HorizontalDisplayAdPage() {
//    val pagerState = rememberPagerState { 3 }
//    HorizontalPager(
//        state = pagerState,
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(200.dp)
//    ) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            colors = CardDefaults.cardColors(
//                containerColor = Color(0xFF3D5AFE)
//            )
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            ) {
//                Text(
//                    "Multiple\nInvoice Formats",
//                    style = MaterialTheme.typography.headlineMedium,
//                    color = Color.White
//                )
//                Button(
//                    onClick = { /* TODO */ },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.White
//                    )
//                ) {
//                    Text("Know More", color = Color.Black)
//                }
//            }
//        }
//    }
//}