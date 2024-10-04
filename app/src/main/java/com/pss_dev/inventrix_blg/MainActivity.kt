package com.pss_dev.inventrix_blg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.identity.Identity
import com.pss_dev.inventrix_blg.GoogleSignIn.GoogleAuthClient
import com.pss_dev.inventrix_blg.GoogleSignIn.OneTapVM
import com.pss_dev.inventrix_blg.GoogleSignIn.SignInScreenWithOneTap
import com.pss_dev.inventrix_blg.screens.HomeScreen
import com.pss_dev.inventrix_blg.screens.OneTapSignInScreen
import com.pss_dev.inventrix_blg.ui.theme.Inventrix_blgTheme
import com.pss_dev.inventrix_blg.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val googleAuthClient by lazy {
        GoogleAuthClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            Inventrix_blgTheme {
                val authViewModel: AuthViewModel = hiltViewModel()
//                MyApp(authViewModel)
                val viewmodel = viewModel<OneTapVM>()
                val state by viewmodel.state.collectAsStateWithLifecycle()
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if(result.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                            val signInResult = googleAuthClient.signInWithIntent(
                                intent = result.data?:return@launch
                            )
                                viewmodel.onSignInResult(signInResult)
                            }
                        }
                    }
                )

            }
        }
    }
}