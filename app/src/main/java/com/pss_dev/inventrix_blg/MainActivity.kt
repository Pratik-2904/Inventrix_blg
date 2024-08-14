package com.pss_dev.inventrix_blg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.pss_dev.inventrix_blg.navigation.Navigation
import com.pss_dev.inventrix_blg.ui.theme.Inventrix_blgTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            Inventrix_blgTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)
            }
        }
    }
}
