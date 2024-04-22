package vn.hoanguyen.learn.chatapp.compose.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    fun provideFireStore() = Firebase.firestore

    @Provides
    fun provideFireAuth() = FirebaseAuth.getInstance()

}