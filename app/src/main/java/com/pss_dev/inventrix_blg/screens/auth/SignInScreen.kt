package com.example.stockinvoice.screens.signInScreen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pss_dev.inventrix_blg.R
import com.pss_dev.inventrix_blg.navigation.Screens
import com.pss_dev.inventrix_blg.viewModel.AuthViewModel
import com.pss_dev.inventrix_blg.widget.Buttoncomposable
import com.pss_dev.inventrix_blg.widget.ClickableLogInTextComponent
import com.pss_dev.inventrix_blg.widget.CustomTextField
import com.pss_dev.inventrix_blg.widget.DividerTextField
import com.pss_dev.inventrix_blg.widget.GoogleSignInButton
import com.pss_dev.inventrix_blg.widget.HeadingTextComponent
import com.pss_dev.inventrix_blg.widget.NormalTextComponent
import com.pss_dev.inventrix_blg.widget.PasswordTextField

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignInScreen(
    navController: NavController = rememberNavController(),
    authviewModel: AuthViewModel = hiltViewModel()
) {

    //Todo authentication validation
    val keyboardController = LocalSoftwareKeyboardController.current
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val themeBackGroundColor = MaterialTheme.colorScheme.background
    val themeSurfaceColor = MaterialTheme.colorScheme.surface
    val themeTextColor = MaterialTheme.colorScheme.onBackground
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(themeBackGroundColor)
            .padding(28.dp)
            .pointerInput(Unit) {
                // Handle clicks on the box
                this.detectTapGestures(onTap = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                })
            }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(themeBackGroundColor),
            verticalArrangement = Arrangement.Center
        ) {
            item {
                NormalTextComponent(
                    text = stringResource(id = R.string.hello),
                    themeTextColor = themeTextColor,
                )
                HeadingTextComponent(
                    value = stringResource(id = R.string.welcome),
                    themeTextColor = themeTextColor,
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextField(
                    labelValue = "Email",
                    value = emailState,
                    icon = Icons.Default.Email,
                    themeTextColor = themeTextColor,
                    themeSurfaceColor = themeSurfaceColor,
                    keyboardController,
                    focusManager
                )
                Spacer(modifier = Modifier.height(8.dp))
                PasswordTextField(
                    labelValue = "Password",
                    value = passwordState,
                    icon = Icons.Default.Lock,
                    themeTextColor = themeTextColor,
                    themeSurfaceColor = themeSurfaceColor,
                    keyboardController = keyboardController,
                    focusManager = focusManager
                )
                Spacer(modifier = Modifier.heightIn(20.dp))
                Buttoncomposable(
                    value = "LogIn",
                    themebackgroundColor = themeBackGroundColor,
                    themeTextColor = themeTextColor,
                    themeSurfaceColor = themeSurfaceColor,
                    onClick = {
                        //Todo add login logic
                        authviewModel.authenticate(
                            emailState.value,
                            passwordState.value
                        )
                        val sucess = authviewModel.isLoggedIn.observeAsState()
                        if (sucess.value == true) {
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                            navController.navigate(route = Screens.home.route)
                        }
                    }
                )

                Spacer(modifier = Modifier.heightIn(40.dp))

                DividerTextField(
                    value = " or ",
                    themeTextColor = themeTextColor,
                )

                Spacer(modifier = Modifier.heightIn(40.dp))

                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = CardDefaults.outlinedCardBorder(),
                    modifier = Modifier.wrapContentSize(align = Alignment.Center)
                ) {
                    GoogleSignInButton(
                        themebackgroundColor = themeBackGroundColor,
                        themeTextColor = themeTextColor,
                        themeSurfaceColor = themeSurfaceColor
                    )
                }

                Spacer(modifier = Modifier.height(90.dp))

                ClickableLogInTextComponent(
                    themeTextColor = themeTextColor,
                    string = "Don't have an account? ",
                    clickableString = "SignUp",
                    onTextSelected = {
//                        TOdo navigate to signUp
                        navController.navigate(route = Screens.register.route)
                    }
                )

            }

        }

    }

}



