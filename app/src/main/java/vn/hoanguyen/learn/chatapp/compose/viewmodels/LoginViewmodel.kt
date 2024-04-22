package vn.hoanguyen.learn.chatapp.compose.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.hoanguyen.learn.chatapp.compose.services.auth.AuthService
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    val emailText = mutableStateOf("")
    val passText = mutableStateOf("")
    val loading = mutableStateOf(false)
    val error = mutableStateOf("")

    fun login(navigateToHome: () -> Unit) {
        if (emailText.value.isEmpty() || passText.value.isEmpty()) {
            error.value = "Email or Pass is invalid"
            return
        }
        loading.value = true
        viewModelScope.launch {
            try {
                authService.signInWithEmailAndPassword(emailText.value, passText.value)
                navigateToHome.invoke()
            } catch (e: Exception) {
                error.value = "[${e.message}]"
            }
            loading.value = false
        }
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