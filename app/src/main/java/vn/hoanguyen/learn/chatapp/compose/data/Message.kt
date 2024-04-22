package vn.hoanguyen.learn.chatapp.compose.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    val senderID: String = "",
    val senderEmail: String = "",
    val receiverID: String = "",
    val message: String = "",
    val timestamp: Timestamp = Timestamp.now(),
) : Parcelable
