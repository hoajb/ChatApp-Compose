package vn.hoanguyen.learn.chatapp.compose

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        //https://github.com/alirezaeiii/TMDb-Compose-Playground
       // https://github.com/vinchamp77/Demo_SimpleNavigationCompose/blob/master/app/src/main/java/com/example/simplenavigationcompose/ui/navigation/NavRoute.kt
    }
}