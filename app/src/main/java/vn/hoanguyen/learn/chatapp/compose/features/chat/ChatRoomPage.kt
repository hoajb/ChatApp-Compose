package vn.hoanguyen.learn.chatapp.compose.features.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.hoanguyen.learn.chatapp.compose.features.chat.components.MessageList
import vn.hoanguyen.learn.chatapp.compose.ui.theme.GrayBG

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomPage(
    viewmodel: ChatRoomViewmodel = hiltViewModel(),
    otherUserEmail: String,
    navigationBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(otherUserEmail)
                },
                navigationIcon = {
                    IconButton(onClick = navigationBack) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        val messageListFlow by viewmodel.messageListFlow.collectAsState(initial = emptyList())
        LaunchedEffect(Unit) {
            viewmodel.loadMessageList()
        }

        val state = rememberPullToRefreshState()
        if (state.isRefreshing) {
            LaunchedEffect(true) {
                viewmodel.loadMessageList()
                state.endRefresh()
            }
        }

        val messageSent by viewmodel.messageSent.collectAsState(false)
        val scrollState: LazyListState = rememberLazyListState()
        val scope = rememberCoroutineScope()

        LaunchedEffect(messageSent) {
            if (messageSent) {
                scope.launch {
                    delay(100)
                    scrollState.animateScrollToItem(
                        index = Int.MAX_VALUE,
                    )
                }
                viewmodel.messageHandled()
            }
        }

        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(innerPadding)
                    .background(GrayBG)
            ) {

                MessageList(messageListFlow, scrollState)

                PullToRefreshContainer(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = state,
                )
            }

            val chatText = rememberSaveable { mutableStateOf("") }
            Row(
                Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .background(Color.White),
            ) {
                val focusManager = LocalFocusManager.current
                val focusRequester = FocusRequester()

                val onSend: () -> Unit = {
                    viewmodel.sendMessage(
                        message = chatText.value,
                    )
                    chatText.value = ""
                }
                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    value = chatText.value,
                    maxLines = 4,
                    onValueChange = { chatText.value = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        onSend.invoke()
                    }),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    placeholder = { Text("Chat message") },
                )
                Spacer(modifier = Modifier.size(10.dp))

                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = onSend
                ) {
                    Icon(Icons.AutoMirrored.Rounded.Send, contentDescription = "")
                }
            }
        }

    }
}