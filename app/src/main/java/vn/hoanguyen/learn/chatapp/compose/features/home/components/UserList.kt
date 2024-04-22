package vn.hoanguyen.learn.chatapp.compose.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.hoanguyen.learn.chatapp.compose.data.User
import vn.hoanguyen.learn.chatapp.compose.ui.theme.ChatAppComposeTheme

@Composable
fun UserList(
    userList: List<User>,
    onItemClick: (User) -> Unit
) {
    if (userList.isEmpty()) {
        Box(Modifier.fillMaxSize()) {
            Text("Empty", Modifier.align(Alignment.Center))
        }
    } else {
        LazyColumn {
            items(userList) { user ->
                UserItem(user) {
                    onItemClick.invoke(user)
                }
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 60.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.White)
            .clickable { onClick.invoke() }
    ) {
        Text(
            text = user.email,
            Modifier
                .align(Alignment.CenterStart)
                .padding(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserItemPrev() {
    ChatAppComposeTheme {
        Surface {
            UserItem(
                User(
                    email = "sample@gmail.com",
                    displayName = "Sample"
                )
            ) {}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserListPrev() {
    ChatAppComposeTheme {
        Surface {
            UserList(
                (0..10).map {
                    User(
                        email = "sample${it}@gmail.com",
                        displayName = "Sample $it"
                    )
                }
            ) {}
        }
    }
}