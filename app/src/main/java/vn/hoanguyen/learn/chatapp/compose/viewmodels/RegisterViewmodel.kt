package vn.hoanguyen.learn.chatapp.compose.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.hoanguyen.learn.chatapp.compose.services.auth.AuthService
import javax.inject.Inject

@HiltViewModel
class RegisterViewmodel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    val emailText = mutableStateOf("")
    val passText = mutableStateOf("")
    val rePassText = mutableStateOf("")
    val error = mutableStateOf("")
    val loading = mutableStateOf(false)

    fun signUp(
        navigateToHome: () -> Unit
    ) {
        val email = emailText.value.trim()
        val pass = passText.value.trim()
        val rePass = rePassText.value.trim()
        if (email.isEmpty() || pass.isEmpty() || rePass.isEmpty()) {
            error.value = "Please fill in email, password, re-password"
            return
        }

        if (pass != rePass) {
            error.value = "Password is not matched"
            return
        }

        loading.value = true
        viewModelScope.launch {
            try {
                authService.signUpWithEmailAndPassword(email, pass)

                navigateToHome.invoke()
            } catch (e: Exception) {
                error.value = "[${e.message}]"
            }
        }
        loading.value = false
    }

    fun onLoginByGoogle() {
        error.value = "Feature is under development"
    }

    fun onLoginByFacebook() {
        error.value = "Feature is under development"
    }

    fun onLoginByApple() {
        error.value = "Feature is under development"
    }
}