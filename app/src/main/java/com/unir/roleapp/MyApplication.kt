package com.unir.roleapp

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

/**
 * MyApplication actúa como el punto de entrada de la inyección de dependencias. Al estar anotada con @HiltAndroidApp, habilita Hilt en toda la app.
 * */
@HiltAndroidApp
class MyApplication: Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        context = this
    }

}
