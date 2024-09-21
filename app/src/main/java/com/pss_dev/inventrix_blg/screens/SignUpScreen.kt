package com.pss_dev.inventrix_blg.screens

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
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

@Composable
fun SignUpScreen(
    navController: NavController = rememberNavController(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val firstNameState = rememberSaveable { mutableStateOf("") }
    val lastNameState = rememberSaveable { mutableStateOf("") }
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    val themeBackGroundColor = MaterialTheme.colorScheme.background
    val themeSurfaceColor = MaterialTheme.colorScheme.surface
    val themeTextColor = MaterialTheme.colorScheme.primary

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager: FocusManager = LocalFocusManager.current

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
            },
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
                    value = stringResource(id = R.string.create_account),
                    themeTextColor = themeTextColor,
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextField(
                    labelValue = "First Name",
                    value = firstNameState,
                    icon = Icons.Default.Person,
                    themeTextColor = themeTextColor,
                    themeSurfaceColor = themeSurfaceColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomTextField(
                    labelValue = "Last Name",
                    value = lastNameState,
                    icon = Icons.Default.Person,
                    themeTextColor = themeTextColor,
                    themeSurfaceColor = themeSurfaceColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomTextField(
                    labelValue = "Email",
                    value = emailState,
                    icon = Icons.Default.Email,
                    themeTextColor = themeTextColor,
                    themeSurfaceColor = themeSurfaceColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                PasswordTextField(
                    labelValue = "Password",
                    value = passwordState,
                    icon = Icons.Default.Lock,
                    themeTextColor = themeTextColor,
                    themeSurfaceColor = themeSurfaceColor
                )
                Spacer(modifier = Modifier.heightIn(20.dp))
                Buttoncomposable(
                    value = "Register",
                    themebackgroundColor = themeBackGroundColor,
                    themeTextColor = themeTextColor,
                    themeSurfaceColor = themeSurfaceColor,
                    onClick = {
                        authViewModel.signup(
                            firstNameState.value,
                            lastNameState.value,
                            emailState.value,
                            passwordState.value,
                            context
                        )
                        navController.navigate(route = Screens.login.route)
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

                Spacer(modifier = Modifier.height(50.dp))

                ClickableLogInTextComponent(
                    themeTextColor = themeTextColor,
                    string = "Already have an account? ",
                    clickableString = "LogIn",
                    onTextSelected = {
                        navController.navigate(route = Screens.login.route)
                    }
                )
            }
        }
    }
}
