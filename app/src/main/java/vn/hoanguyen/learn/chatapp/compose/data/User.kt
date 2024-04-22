package vn.hoanguyen.learn.chatapp.compose.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uuid: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoURL: String = "",
) : Parcelable
