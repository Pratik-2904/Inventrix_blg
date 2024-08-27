package com.pss_dev.inventrix_blg.screens

import android.content.Context
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pss_dev.inventrix_blg.R
import com.pss_dev.inventrix_blg.viewModel.AuthViewModel
import kotlinx.coroutines.delay

@Preview
@Composable
fun SplashScreen(
    navController: NavHostController = rememberNavController(),
) {
    val viewModel: AuthViewModel = AuthViewModel()
    val context: Context = LocalContext.current
    val isLoggedIn = viewModel.isLoggedIn()
    val scale = remember { androidx.compose.animation.core.Animatable(0f) }

    // Start the scale animation
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 0.6f,
            animationSpec = tween(durationMillis = 3000,
                easing = { OvershootInterpolator(3f).getInterpolation(it) })
        )
        delay(3000L)
    }

    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.secondaryContainer
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(80.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                    )
                    .border(
                        BorderStroke(
                            2.dp, brush = Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.onPrimaryContainer,
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(20.dp),

                ) {
                Image(
                    painter = painterResource(
                        id = if (isSystemInDarkTheme()) {
                            R.drawable.infinix_2
                        } else {
                            R.drawable.infinix_1
                        }
                    ),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .scale((scale.value * 1.5).toFloat())
                        .clip(RoundedCornerShape(20.dp))
                )
            }
        }
    }

    LaunchedEffect(isLoggedIn) {
        delay(3000L)
        if (isLoggedIn) {
            Toast.makeText(context, "Welcome ${viewModel.getusername()}", Toast.LENGTH_LONG).show()
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            Toast.makeText(context, "Login First", Toast.LENGTH_LONG).show()
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }
}
