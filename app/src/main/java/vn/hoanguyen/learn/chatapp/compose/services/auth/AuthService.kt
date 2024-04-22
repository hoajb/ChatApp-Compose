package vn.hoanguyen.learn.chatapp.compose.services.auth

import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import vn.hoanguyen.learn.chatapp.compose.constants.FireStoreCollectionConstant
import vn.hoanguyen.learn.chatapp.compose.constants.FireStoreUserConstant
import javax.inject.Inject

class AuthService @Inject constructor() {
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val fireStore by lazy { Firebase.firestore }

    suspend fun signInWithEmailAndPassword(email: String, password: String) {
        try {
            val userCredentialResult = withContext(Dispatchers.IO) {
                auth.signInWithEmailAndPassword(email, password).await()
            }
            if (userCredentialResult.user == null) {
                throw Exception("userCredentialResult.user==null")
            }

            withContext(Dispatchers.IO) {
                createUser(userCredentialResult)
            }
        } catch (e: FirebaseAuthException) {
            throw Exception(e.errorCode)
        }
    }

    suspend fun signUpWithEmailAndPassword(email: String, password: String) {
        try {
            val userCredentialResult = withContext(Dispatchers.IO) {
                auth.createUserWithEmailAndPassword(email, password).await()
            }
            if (userCredentialResult.user == null) {
                throw Exception("userCredentialResult.user==null")
            }

            withContext(Dispatchers.IO) {
                createUser(userCredentialResult)
            }
        } catch (e: FirebaseAuthException) {
            throw Exception(e.errorCode)
        }
    }

    suspend fun signOut() {
        try {
            withContext(Dispatchers.IO) {
                auth.signOut()
            }
        } catch (e: FirebaseAuthException) {
            throw Exception(e.errorCode)
        }
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun isLoggedIn(): Boolean = getCurrentUser() != null

    private fun createUser(credential: AuthResult) {
        fireStore
            .collection(FireStoreCollectionConstant.users)
            .document(credential.user!!.uid)
            .set(
                hashMapOf(
                    FireStoreUserConstant.userUuid to credential.user!!.uid,
                    FireStoreUserConstant.userEmail to credential.user!!.email,
                    FireStoreUserConstant.userDisplayName to credential.user!!.displayName,
                    FireStoreUserConstant.userPhotoURL to credential.user!!.photoUrl
                )
            )
    }
}