package vn.hoanguyen.learn.chatapp.compose.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.hoanguyen.learn.chatapp.compose.data.User
import vn.hoanguyen.learn.chatapp.compose.services.auth.AuthService
import vn.hoanguyen.learn.chatapp.compose.services.chat.ChatService
import vn.hoanguyen.learn.chatapp.compose.ui.state.UIState
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val authService: AuthService,
    private val chatService: ChatService,
) : ViewModel() {
    companion object {
        const val TAG = "HomeViewmodel"
    }

    private val _userListUIState = MutableStateFlow<UIState<List<User>>>(UIState.Loading)
    val userListUIState = _userListUIState.asStateFlow()

    fun logOut() {
        viewModelScope.launch {
            try {
                authService.signOut()
            } catch (_: Exception) {
            }
        }
    }

    fun loadUserList() {
        viewModelScope.launch {
            _userListUIState.emit(UIState.Loading)
            try {
                val usersList = chatService.getUsersList()
                Log.d(TAG, "usersList: $usersList")
                _userListUIState.emit(
                    UIState.Loaded(
                        usersList
                    )
                )
            } catch (_: Exception) {
                _userListUIState.emit(UIState.Error())
            }
        }
    }
}