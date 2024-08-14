package com.pss_dev.inventrix_blg.screens

import android.content.Context
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
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
import com.pss_dev.inventrix_blg.ui.theme.Pink80
import kotlinx.coroutines.delay

@Preview
@Composable
fun SplashScreen(navController: NavHostController = rememberNavController()) {

    //Todo Check if user is logged in
    //Todo If yes fetch its details and navigate to HomePage

    val isLoggedIn = remember {
        mutableStateOf(false)
    }

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    val context: Context = LocalContext.current


    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 3000,
//                delayMillis = 2000,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }
            )
        )
        delay(3000L)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    listOf(
//                        Purple80,
//                        Pink80

                        //with the user on MAterial Colors
                        androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer,
                        androidx.compose.material3.MaterialTheme.colorScheme.secondaryContainer
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(color = Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(80.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Pink80
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .scale((scale.value * 1.5).toFloat())
                        .clip(
                            shape = RoundedCornerShape(20.dp)
                        )
                )
            }
        }
    }
    LaunchedEffect(key1 = true) {
        delay(3000L)
        if (isLoggedIn.value) {
            Toast.makeText(context, "Login First", Toast.LENGTH_LONG).show()
            //Todo Navigate to LoginPage
            navController.navigate("login") {
                //remove splash from backstack
                popUpTo("splash") {
                    inclusive = true
                }
            }
        } else {
            Toast.makeText(context, "Welcome User", Toast.LENGTH_LONG).show()
            //Todo Navigate to HomePage
            navController.navigate("home")
        }
    }
}