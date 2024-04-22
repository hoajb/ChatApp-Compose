package vn.hoanguyen.learn.chatapp.compose.constants

class FireStoreCollectionConstant {
    companion object {
        const val users: String = "Users"
        const val chatRooms: String = "ChatRooms"
        const val messages: String = "Messages"
    }
}

class FireStoreUserConstant {
    companion object {
        const val userUuid: String = "uuid"
        const val userEmail: String = "email"
        const val userDisplayName: String = "displayName"
        const val userPhotoURL: String = "photoURL"

    }
}