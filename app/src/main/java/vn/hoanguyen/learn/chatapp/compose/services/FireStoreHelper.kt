package vn.hoanguyen.learn.chatapp.compose.services

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import dagger.Lazy
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FireStoreHelper @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    companion object {
        private const val TAG = "FireStoreHelper"
    }

    suspend fun <T> getData(collection: String, transform: (QueryDocumentSnapshot) -> T): List<T> =
        suspendCoroutine { continuation ->
            val docRef = firestore.collection(collection)
            docRef.get()
                .addOnSuccessListener { result ->
                    val list = mutableListOf<T>()
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        list.add(transform(document))
                    }
                    continuation.resume(list)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                    continuation.resumeWithException(exception)
                }
        }
}