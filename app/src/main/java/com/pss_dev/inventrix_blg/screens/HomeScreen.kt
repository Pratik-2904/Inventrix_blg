package com.pss_dev.inventrix_blg.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pss_dev.inventrix_blg.widget.CustomBottomBar
import com.pss_dev.inventrix_blg.widget.CustomTopBar

@Preview(showBackground = true)
@Composable
fun HomeScreen() {

    val companyName = remember {
        mutableStateOf("ShivShakti Enterprises")
    }
    val BottomSheetState = remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            CustomTopBar(modifier = Modifier.padding(2.dp),
                title = companyName.value, imageVector = Icons.Default.KeyboardArrowDown,
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
                    //Todo Implement the Bottom Sheet for more companies and
                }
            )

        },
        bottomBar = {
            CustomBottomBar()
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.Transparent)
        ) {

        }
    }
}