package vn.hoanguyen.learn.chatapp.compose.features.chat.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import vn.hoanguyen.learn.chatapp.compose.ui.theme.ChatAppComposeTheme

data class MessageUI(
    val messageText: String, val isYourMessage: Boolean, val timeString: String
)

@Composable
fun MessageList(
    messageList: List<MessageUI>,
    scrollState: LazyListState = rememberLazyListState()
) {
    LaunchedEffect(scrollState) {
        scrollState.animateScrollToItem(
            index = Int.MAX_VALUE,
        )
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = scrollState
    ) {
        items(messageList) { message ->
            MessageItem(message)
        }
    }

}

@Composable
fun MessageItem(
    message: MessageUI,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val maxWidth = screenWidth * 2 / 3

    val (timeVisibility, setTimeVisibility) = remember {
        mutableStateOf(false)
    }

    Box(
        Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier
            .align(if (message.isYourMessage) Alignment.CenterEnd else Alignment.CenterStart)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .widthIn(0.dp, maxWidth)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp)
            )
            .background(if (message.isYourMessage) Color.White else Color.LightGray)
            .clickable { setTimeVisibility(!timeVisibility) }) {
            Text(
                modifier = Modifier
                    .align(if (message.isYourMessage) Alignment.End else Alignment.Start)
                    .padding(10.dp),
                text = message.messageText,
                color = Color.Black
            )

            AnimatedVisibility(visible = timeVisibility) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 10.dp, vertical = 0.dp),
                    text = message.timeString,
                    color = if (message.isYourMessage) Color.LightGray else Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserItemPrev() {
    ChatAppComposeTheme {
        Surface {
            MessageItem(
                MessageUI(
                    messageText = "My message",
                    isYourMessage = false,
                    timeString = "2024-03-01 12:34"
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserListPrev() {
    ChatAppComposeTheme {
        Surface {
            MessageList((0..10).map { pos ->
                MessageUI(
                    messageText = "My message $pos",
                    isYourMessage = pos % 3 != 0,
                    timeString = "2024-03-01 12:34"
                )
            })
        }
    }
}