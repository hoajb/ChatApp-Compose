package vn.hoanguyen.learn.chatapp.compose.features.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import vn.hoanguyen.learn.chatapp.compose.features.auth.AuthViewmodel

@Composable
fun SplashPage(
    authViewmodel: AuthViewmodel = hiltViewModel(),
    onNavigateLogin: () -> Unit,
    onNavigateHome: () -> Unit,
) {
    LaunchedEffect(Unit) {
        authViewmodel.onNavigateHandle(
            onNavigateLogin,
            onNavigateHome
        )
    }
}