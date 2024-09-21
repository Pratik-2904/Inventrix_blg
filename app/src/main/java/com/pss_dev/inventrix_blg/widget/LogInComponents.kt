package com.pss_dev.inventrix_blg.widget

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pss_dev.inventrix_blg.R
import com.pss_dev.inventrix_blg.ui.theme.PrimaryColor
import com.pss_dev.inventrix_blg.ui.theme.SecondaryColor
import com.pss_dev.inventrix_blg.ui.theme.TextColor
import com.pss_dev.inventrix_blg.ui.theme.componentShapes

@Preview
@Composable
fun NormalTextComponent(
    text: String = "Sample", themeTextColor: Color = Color.Black
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp), text = text, style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            color = themeTextColor
        ), textAlign = TextAlign.Center

    )
}

@Composable
fun HeadingTextComponent(
    value: String,
    themeTextColor: Color,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(0.dp), text = value, style = TextStyle(
            fontSize = 30.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal

        ), color = themeTextColor, textAlign = TextAlign.Center
    )
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    labelValue: String = "label",
    value: MutableState<String> = remember { mutableStateOf("value") },
    icon: ImageVector = Icons.Default.Email,
    themeTextColor: Color = TextColor,
    themeSurfaceColor: Color = Color.White,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    focusManager: FocusManager = LocalFocusManager.current
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = themeSurfaceColor)
    ) {
        OutlinedTextField(modifier = Modifier
            .fillMaxWidth()
            .background(color = themeSurfaceColor),
            label = { Text(text = labelValue, color = themeTextColor) },
            value = value.value,
            singleLine = true,
            onValueChange = { value.value = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next

            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() },
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = themeTextColor,
                focusedLabelColor = themeTextColor,
                cursorColor = themeTextColor,
            ),
            shape = MaterialTheme.shapes.small,
            leadingIcon = {
                Icon(imageVector = icon, contentDescription = "Icon", tint = themeTextColor)
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    labelValue: String,
    icon: ImageVector,
    themeTextColor: Color,
    value: MutableState<String>,
    themeSurfaceColor: Color,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    onDone: () -> Unit = {},
    focusManager: FocusManager = LocalFocusManager.current
) {
    val isPasswordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = themeSurfaceColor),
        label = { Text(text = labelValue) },
        value = value.value,
        singleLine = true,
        onValueChange = { value.value = it },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = themeTextColor,
            focusedLabelColor = themeTextColor,
            cursorColor = themeTextColor,

            ),
        shape = componentShapes.small,
        leadingIcon = {
            Icon(imageVector = icon, contentDescription = "Icon", tint = themeTextColor)
        },
        trailingIcon = {
            val icon2 = if (!isPasswordVisible.value) {
                R.drawable.hidden
            } else {
                R.drawable.eye
            }
            val description = if (isPasswordVisible.value) {
                "Hide Password"
            } else {
                "Show Password"
            }
            IconButton(onClick = { isPasswordVisible.value = !isPasswordVisible.value }) {
                Icon(
                    modifier = Modifier.aspectRatio(0.6f),
                    painter = painterResource(id = icon2),
                    contentDescription = description,
                    tint = themeTextColor,
                )
            }
        },
        visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()

    )
}

@Composable
fun CheckBoxComponent(
    value: String,
    themebackgroundColor: MutableState<Color>,
    themeTextColor: MutableState<Color>,
    themeSurfaceColor: MutableState<Color>,
    onTextSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkedState = remember { mutableStateOf(false) }
        Checkbox(checked = checkedState.value, onCheckedChange = {
            checkedState.value = it
        })

        ClickableTextComponent(
            value = value,
            onTextSelected = onTextSelected,
            themebackgroundColor = themebackgroundColor,
            themeTextColor = themeTextColor,
            themeSurfaceColor = themeSurfaceColor
        )

    }

}

@Composable
fun ClickableTextComponent(
    value: String,
    themebackgroundColor: MutableState<Color>,
    themeTextColor: MutableState<Color>,
    themeSurfaceColor: MutableState<Color>,
    onTextSelected: (String) -> Unit
) {

    val initialString = "By accepting you accept our "
    val terms = " Terms and Conditions"
    val andText = " and "
    val privacy = "Privacy Policy"
    val annotatedString = buildAnnotatedString {
        append(initialString)
        withStyle(style = SpanStyle(color = PrimaryColor)) {
            pushStringAnnotation(tag = privacy, annotation = privacy)
            append(privacy)
        }
        append(andText)
        withStyle(style = SpanStyle(color = PrimaryColor)) {
            pushStringAnnotation(tag = terms, annotation = terms)
            append(terms)
        }
    }
    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
            Log.d("Clickable Message", "{$span}")
            //Todo on click on terms and conditions
            if (span.item == terms) {
                onTextSelected(span.item)
            }

        }
    })
}

@Composable
fun Buttoncomposable(
    value: String,
    themebackgroundColor: Color,
    themeTextColor: Color,
    themeSurfaceColor: Color,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(SecondaryColor, PrimaryColor)),
                    shape = RoundedCornerShape(50.dp)
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = value, style = TextStyle(
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            )

        }
    }
}

@Composable
fun DividerTextField(
    value: String,
    themeTextColor: Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), color = themeTextColor, thickness = 1.dp
        )
        Text(
            text = value, fontSize = 18.sp, color = themeTextColor
        )
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), color = themeTextColor, thickness = 1.dp
        )

    }
}

@Composable
fun GoogleSignInButton(
    themebackgroundColor: Color, themeTextColor: Color, themeSurfaceColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = { /*TODO Addition of One click Sign In Logic*/ }) {
            Image(
                modifier = Modifier.aspectRatio(1f),
                painter = painterResource(id = R.drawable.icon_google),
                contentDescription = "Google Login"
            )
        }

    }
}

@Composable
fun ClickableLogInTextComponent(
    themeTextColor: Color,
    string: String = "Already Have An account? ",
    clickableString: String = "LogIn",
    onTextSelected: (String) -> Unit
) {

    val annotatededString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = themeTextColor)) {
            append(string)
        }
        withStyle(style = SpanStyle(color = PrimaryColor)) {
            pushStringAnnotation(tag = clickableString, annotation = clickableString)
            append(clickableString)
        }
    }
    ClickableText(modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 40.dp), style = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
        textAlign = TextAlign.Center
    ), text = annotatededString, onClick = { offset ->
        annotatededString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
            Log.d("Clickable Message", "{$span}")
            //Todo on string
            if (span.item == clickableString) {
                onTextSelected(span.item)
            }

        }
    })
}

