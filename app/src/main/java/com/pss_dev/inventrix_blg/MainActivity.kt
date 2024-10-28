package com.pss_dev.inventrix_blg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.pss_dev.inventrix_blg.ui.theme.Inventrix_blgTheme
import com.pss_dev.inventrix_blg.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    private val googleAuthClient by lazy {
//        GoogleAuthClient(
//            context = applicationContext,
//            oneTapClient = Identity.getSignInClient(applicationContext)
//        )
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            Inventrix_blgTheme {
                val authViewModel: AuthViewModel = hiltViewModel()
                MyApp(authViewModel)
//                val viewmodel = viewModel<OneTapVM>()
//                val state by viewmodel.state.collectAsStateWithLifecycle()
//                val launcher = rememberLauncherForActivityResult(
//                    contract = ActivityResultContracts.StartIntentSenderForResult(),
//                    onResult = { result ->
//                        if (result.resultCode == RESULT_OK) {
//                            lifecycleScope.launch {
//                                val signInResult = googleAuthClient.signInWithIntent(
//                                    intent = result.data ?: return@launch
//                                )
//                                viewmodel.onSignInResult(signInResult)
//                            }
//                        }
//                    }
//                )

            }
        }
    }
}