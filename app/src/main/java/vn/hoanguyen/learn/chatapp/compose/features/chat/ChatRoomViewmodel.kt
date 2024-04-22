package vn.hoanguyen.learn.chatapp.compose.features.chat

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import vn.hoanguyen.learn.chatapp.compose.data.Message
import vn.hoanguyen.learn.chatapp.compose.features.chat.components.MessageUI
import vn.hoanguyen.learn.chatapp.compose.navigation.NavConstants
import vn.hoanguyen.learn.chatapp.compose.services.auth.AuthService
import vn.hoanguyen.learn.chatapp.compose.services.chat.ChatService
import vn.hoanguyen.learn.chatapp.compose.ui.state.UIState
import vn.hoanguyen.learn.chatapp.compose.utils.DateTimeUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel()
class ChatRoomViewmodel @Inject constructor(
    private val chatService: ChatService,
    private val dateTimeUtils: DateTimeUtils,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        const val TAG = "HomeViewmodel"
    }

    private val receiverId: String = savedStateHandle.get<String>(NavConstants.ArgUserId).orEmpty()
    private val _messageListFlow: MutableStateFlow<List<MessageUI>> = MutableStateFlow(emptyList())
    val messageListFlow: Flow<List<MessageUI>> = _messageListFlow.asStateFlow()

    private val _messageSent: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val messageSent: Flow<Boolean> = _messageSent.asStateFlow()

    fun loadMessageList() {
        viewModelScope.launch {
            chatService.getMessagesList(receiverId).collectLatest {
                Log.d(TAG, "List Message: $it")
                _messageListFlow.emit(it.map { message ->
                    MessageUI(
                        messageText = message.message,
                        isYourMessage = chatService.isYourMessage(message),
                        timeString = dateTimeUtils.formatTimestamp(message.timestamp.toDate().time)
                    )
                })

                _messageSent.emit(true)
            }
        }
    }

    fun messageHandled() {
        viewModelScope.launch {
            _messageSent.emit(false)
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            try {
                chatService.sendMessage(
                    receiverId = receiverId,
                    message = message
                )
            } catch (_: Exception) {
                //todo
            }
        }
    }
}