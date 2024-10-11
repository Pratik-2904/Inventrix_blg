package com.pss_dev.inventrix_blg.screens.NavbarScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pss_dev.inventrix_blg.data.model.BusinessInfo
import com.pss_dev.inventrix_blg.data.model.DashboardUiState
import com.pss_dev.inventrix_blg.data.model.Feature
import com.pss_dev.inventrix_blg.data.model.Summary
import com.pss_dev.inventrix_blg.screens.CommonScreenLayout
import com.pss_dev.inventrix_blg.viewModel.AuthViewModel
import com.pss_dev.inventrix_blg.viewModel.DashboardViewModel

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    viewModel: DashboardViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
//    val businessInfoModalState = remember { mutableStateOf(false) }

    CommonScreenLayout(navController) { paddingValues, businessInfoModalState ->
        HomeContent(
            uiState = uiState,
            paddingValues = paddingValues,
            businessInfoModalState = businessInfoModalState
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    uiState: DashboardUiState,
    paddingValues: PaddingValues,
    businessInfoModalState: MutableState<Boolean>
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF1C1B1F)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item { HorizontalDisplayAdPage() }
            item {
                uiState.summary?.let { summary ->
                    SummaryCard(summary = summary)
                }
            }
            item {
                if (businessInfoModalState.value) {
                    ModalBottomSheet(
                        sheetState = rememberModalBottomSheetState(),
                        onDismissRequest = {
                            businessInfoModalState.value = false
                        }
                    ) {
                        BusinessInfoContent(uiState.businessInfo)
                    }
                }
            }
            item { HomeCreateSection() }
            item {
                FeatureGrid(
                    features = uiState.features,
                    modifier = Modifier
                        .height(200.dp)  // Fixed height to prevent scrolling issues
                        .fillMaxWidth()
                )
            }

        }
    }
}

@Composable
private fun BusinessInfoContent(businessInfo: BusinessInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Business Information",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Display business details here in a structured format
        Text(
            text = "Business Name: ${businessInfo.name}",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Address: ${businessInfo.address}",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Contact: ${businessInfo.contact}",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "GST Number: ${businessInfo.gstin}",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )

        // Add more business details if necessary
    }
}

@Composable
fun FeatureGrid(
    features: List<Feature>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier.padding(16.dp),
        userScrollEnabled = false  // Disable scrolling to prevent conflicts
    ) {
        items(features, key = { it.id }) { feature ->
            FeatureItem(feature)
        }
    }
}

@Composable
private fun FeatureItem(feature: Feature) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = feature.icon,
            contentDescription = feature.name,
            tint = Color(0xFF3D5AFE),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = feature.name,
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeTopBar(uiState: DashboardUiState, businessInfoModalState: MutableState<Boolean>) {
    TopAppBar(
        modifier = Modifier.heightIn(15.dp),
        title = {
            Row(
                modifier = Modifier.clickable(
                    onClick = {
                        businessInfoModalState.value = true
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = uiState.businessInfo.name,
                    color = Color.White
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        actions = {
            Text(
                text = "PSS 🇮🇳",
                color = Color.White,
                modifier = Modifier.padding(end = 16.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1C1B1F)
        )
    )
}

@Composable
private fun HomeCreateSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Create",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )
        IconButton(
            onClick = { /* TODO */ },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color(0xFFE91E63)
            )
        ) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Create New", tint = Color.White)
        }
    }
}

@Composable
private fun SummaryCard(summary: Summary) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2D2D2D)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = summary.period,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Period Selector",
                        tint = Color.White
                    )
                }
                Text(
                    "View Bills",
                    color = Color(0xFF3D5AFE),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Sales", color = Color.Gray)
                    Text(
                        text = "₹${summary.sales}",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                Column {
                    Text("Purchases", color = Color.Gray)
                    Text(
                        text = "₹${summary.purchases}",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}

@Composable
private fun HorizontalDisplayAdPage() {
    val pagerState = rememberPagerState { 3 }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) { page ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF3D5AFE)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    "Multiple\nInvoice Formats",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    )
                ) {
                    Text("Know More", color = Color.Black)
                }
            }
        }
    }
}
