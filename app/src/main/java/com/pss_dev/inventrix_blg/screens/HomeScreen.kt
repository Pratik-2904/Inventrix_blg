package com.pss_dev.inventrix_blg.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pss_dev.inventrix_blg.R
import com.pss_dev.inventrix_blg.widget.CustomBottomBar
import com.pss_dev.inventrix_blg.widget.CustomBottomSheet
import com.pss_dev.inventrix_blg.widget.CustomTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    val companyName = remember {
        mutableStateOf("ShivShakti Enterprises")
    }
    val businessDetailsBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    val bussinessInfoSheetState = remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            CustomTopBar(
                modifier = Modifier.padding(2.dp),
                title = companyName.value,
                imageVector = Icons.Default.KeyboardArrowDown,
                actions = {
                    IconButton(onClick = { /*TODO Add drawer and implement*/ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Options",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                onTextButtonClicked = {
                    // Show the bottom sheet
                    bussinessInfoSheetState.value = true
                }
            )
        },
        bottomBar = {
            CustomBottomBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            CustomBottomSheet(
                sheetState = businessDetailsBottomSheetState,
                title = "Business Details",
                onDismissRequest = {
                    bussinessInfoSheetState.value = false
                }
            ) {
                // Content of the bottom sheet
                CompanyInfoBox()
            }
        }
    }
}

@Preview
@Composable
fun CompanyInfoBox(modifier: Modifier = Modifier, title: String? = null) {
    val image = painterResource(id = R.drawable.logo)
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(30.dp),
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            //Todo Add Company Info
            //image ,text, gstn, edit, Share, checkpoint
            RoundedImage(image = image, rowHeight = 45)
            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "ShivShakti", style = MaterialTheme.typography.titleMedium)
                Text(text = "GSTIN: 1234567890", style = MaterialTheme.typography.bodySmall)
                Row(
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(text = "edit", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(text = "share", style = MaterialTheme.typography.bodySmall)

                }
            }
            Checkbox(checked = true, onCheckedChange = null)

        }
    }
}

@Composable
fun RoundedImage(image: Painter, rowHeight: Int) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(rowHeight.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop, // Ensures the image is cropped to fill the bounds
            modifier = Modifier
                .size(rowHeight.dp) // Makes the image size equal to the row height
                .clip(CircleShape) // Clips the image into a circle
        )
    }
}
