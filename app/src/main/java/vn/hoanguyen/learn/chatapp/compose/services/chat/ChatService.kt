package vn.hoanguyen.learn.chatapp.compose.services.chat

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import vn.hoanguyen.learn.chatapp.compose.constants.FireStoreCollectionConstant
import vn.hoanguyen.learn.chatapp.compose.data.Message
import vn.hoanguyen.learn.chatapp.compose.data.User
import vn.hoanguyen.learn.chatapp.compose.services.FireStoreHelper
import vn.hoanguyen.learn.chatapp.compose.services.auth.AuthService
import javax.inject.Inject

class ChatService @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val helper: FireStoreHelper,
    private val authService: AuthService,
) {
    companion object {
        const val TAG = "ChatService"
    }

    suspend fun getUsersList(): List<User> {
        val currentUserId = authService.getCurrentUser()?.uid.orEmpty()
        return helper.getData(FireStoreCollectionConstant.users) { document ->
            Log.d(TAG, document.toString())
            document.toObject(User::class.java)
        }.filterNot { user -> user.uuid == currentUserId }
    }

    fun getMessagesList(receiverId: String): Flow<List<Message>> {
        val currentUserId = authService.getCurrentUser()?.uid.orEmpty()
        val listIds = listOf(currentUserId, receiverId).sorted()
        val chatRoomIds = listIds.joinToString(separator = "_")
        Log.d(TAG, "chatRoomIds: $chatRoomIds")
        val docRef =
            firestore.collection(FireStoreCollectionConstant.chatRooms)
                .document(chatRoomIds)
                .collection(FireStoreCollectionConstant.messages)
                .orderBy("timestamp", Query.Direction.ASCENDING)
        return try {
            docRef.snapshots().map { querySnapshot ->
                querySnapshot.toObjects(Message::class.java)
            }
        } catch (e: Exception) {
            Log.d(TAG, "toObjects - querySnapshot: $e")
            emptyFlow()
        }
    }

    suspend fun sendMessage(receiverId: String, message: String) {
        val currentUserId = authService.getCurrentUser()?.uid.orEmpty()
        val currentUserEmail = authService.getCurrentUser()?.email.orEmpty()
        val timestamp = Timestamp.now()

        val messageObj = Message(
            senderID = currentUserId,
            senderEmail = currentUserEmail,
            receiverID = receiverId,
            message = message,
            timestamp = timestamp
        )

        val listIds = listOf(currentUserId, receiverId).sorted()
        val chatRoomIds = listIds.joinToString(separator = "_")

        firestore.collection(FireStoreCollectionConstant.chatRooms)
            .document(chatRoomIds)
            .collection(FireStoreCollectionConstant.messages)
            .add(messageObj)
    }

    fun isYourMessage(message: Message): Boolean =
        message.senderID == authService.getCurrentUser()?.uid
}