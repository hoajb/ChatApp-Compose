package vn.hoanguyen.learn.chatapp.compose.ui.state

sealed class UIState<out T> {
    data object Loading : UIState<Nothing>()
    data class Error(val errorMessage: String = "") : UIState<Nothing>()
    data class Loaded<T>(val data: T) : UIState<T>()
}
