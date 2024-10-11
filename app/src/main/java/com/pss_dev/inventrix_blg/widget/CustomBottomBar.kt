package com.pss_dev.inventrix_blg.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.GifBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material.icons.filled.Window
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pss_dev.inventrix_blg.data.BottomAppBarScope
import com.pss_dev.inventrix_blg.navigation.Screens
import com.pss_dev.inventrix_blg.navigation.safeNavigate

@Preview
@Composable
fun CustomBottomBar(
    modifier: Modifier = Modifier,
    title: String = "ShivShakti Enterprises",
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = { },
    elevation: Dp = 4.dp,
    onTextButtonClicked: () -> Unit = {},
    imageVector: ImageVector? = null,
    navController: NavController = rememberNavController()
) {
    val selectedItem = remember { mutableStateOf(BottomAppBarScope.Home) }

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
//        color = Color(0xFF1C1B1F),
        shadowElevation = elevation,
        tonalElevation = elevation
    ) {
        val unselectedColor = MaterialTheme.colorScheme.surfaceTint
        val selectedColor = MaterialTheme.colorScheme.onPrimaryContainer

        BottomAppBar(
            modifier = modifier.fillMaxWidth(),
            actions = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomBarIcon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        selectedItem = selectedItem,
                        thisItem = BottomAppBarScope.Home,
                        selectedColor = selectedColor,
                        unselectedColor = unselectedColor,
                        onClick = {
                            navController.safeNavigate(Screens.home.route)
                        }
                    )
                    BottomBarIcon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = "Bills",
                        selectedItem = selectedItem,
                        thisItem = BottomAppBarScope.Bills,
                        selectedColor = selectedColor,
                        unselectedColor = unselectedColor,
                        onClick = {
                            navController.safeNavigate(Screens.bills.route)
                        }
                    )
                    BottomBarIcon(
                        imageVector = Icons.Default.GifBox,
                        contentDescription = "Product",
                        selectedItem = selectedItem,
                        thisItem = BottomAppBarScope.Products,
                        selectedColor = selectedColor,
                        unselectedColor = unselectedColor,
                        onClick = {
                            navController.safeNavigate(Screens.products.route)
                        }
                    )
                    BottomBarIcon(
                        imageVector = Icons.Default.Person4,
                        contentDescription = "Parties",
                        selectedItem = selectedItem,
                        thisItem = BottomAppBarScope.Parties,
                        selectedColor = selectedColor,
                        unselectedColor = unselectedColor,
                        onClick = {
                            navController.safeNavigate(Screens.parties.route)
                        }
                    )
                    BottomBarIcon(
                        imageVector = Icons.Default.Window,
                        contentDescription = "Window",
                        selectedItem = selectedItem,
                        thisItem = BottomAppBarScope.More,
                        selectedColor = selectedColor,
                        unselectedColor = unselectedColor,
                        onClick = {
                            navController.safeNavigate(Screens.more.route)
                        }
                    )
                }
            }
        )
    }
}

@Composable
fun BottomBarIcon(
    imageVector: ImageVector,
    contentDescription: String,
    selectedItem: MutableState<BottomAppBarScope>,
    thisItem: BottomAppBarScope,
    selectedColor: androidx.compose.ui.graphics.Color,
    unselectedColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit = {}
) {
    val tint = if (selectedItem.value == thisItem) selectedColor else unselectedColor
    IconButton(onClick = {
        onClick()
        selectedItem.value = thisItem // Set the selected item
    }) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}