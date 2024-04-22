package vn.hoanguyen.learn.chatapp.compose.features.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Message
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.hoanguyen.learn.chatapp.compose.R
import vn.hoanguyen.learn.chatapp.compose.components.RoundedImageButton
import vn.hoanguyen.learn.chatapp.compose.dialog.AlertDialog
import vn.hoanguyen.learn.chatapp.compose.ui.theme.ChatAppComposeTheme
import vn.hoanguyen.learn.chatapp.compose.viewmodels.LoginViewmodel

@Composable
fun LoginPage(
    loginViewmodel: LoginViewmodel = hiltViewModel(),
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit
) {
    var emailText by rememberSaveable { loginViewmodel.emailText }
    var passText by rememberSaveable { loginViewmodel.passText }
    val loading by rememberSaveable { loginViewmodel.loading }
    val error = loginViewmodel.error

    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(weight = 1f))
        Icon(
            Icons.Rounded.Message,
            modifier = Modifier.size(100.dp),
            contentDescription = stringResource(id = R.string.shopping_cart_content_desc)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "Login", fontWeight = FontWeight.Bold, fontSize = 20.sp
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "Welcome Back! Login to Continue Your Chat Adventure!",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(30.dp))
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = emailText,
            maxLines = 1,
            onValueChange = { emailText = it },
            placeholder = { Text("Email") },
            leadingIcon = { Icon(Icons.Rounded.Email, "") })
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = passText,
            maxLines = 1,
            onValueChange = { passText = it },
            placeholder = { Text("Password") },
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Rounded.Lock, "") },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(
                        if (passwordVisibility.value) Icons.Rounded.VisibilityOff
                        else Icons.Rounded.Visibility, ""
                    )
                }
            },
        )
        Spacer(modifier = Modifier.size(30.dp))

        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 50.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else Button(modifier = Modifier.defaultMinSize(minWidth = 200.dp, minHeight = 50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(15),
            content = { Text("Login") },
            onClick = {
                loginViewmodel.login(navigateToHome)
            })

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundedImageButton(imageRes = R.drawable.ic_google, onClick = {
                loginViewmodel.onLoginByGoogle()
            })
            RoundedImageButton(imageRes = R.drawable.ic_facebook, onClick = {
                loginViewmodel.onLoginByFacebook()
            })
            RoundedImageButton(imageRes = R.drawable.ic_apple, onClick = {
                loginViewmodel.onLoginByApple()
            })
        }
        Spacer(modifier = Modifier.weight(weight = 1f))
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Text("Don't have a account? ")
            Text(
                "Sign up", modifier = Modifier.clickable {
                    navigateToRegister.invoke()
                }, style = LocalTextStyle.current.copy(color = Color.Blue)
            )
        }
        Spacer(modifier = Modifier.size(30.dp))
    }

    when {
        error.value != "" -> {
            AlertDialog(onDismissRequest = { error.value = "" }, onConfirmation = {
                error.value = ""
                println("Confirmation registered")
            }, dialogTitle = "Error", dialogText = error.value, icon = Icons.Default.Error
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPagePreview() {
    ChatAppComposeTheme {
        LoginPage(navigateToRegister = {}, navigateToHome = {})
    }
}