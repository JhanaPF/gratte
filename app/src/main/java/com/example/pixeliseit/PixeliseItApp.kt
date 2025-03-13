package com.example.pixeliseit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PixeliseItApp : Application() {
//    override fun onCreate() {
//        super.onCreate()
//        try {
//            val field = Class.forName("android.database.CursorWindow")
//                .getDeclaredField("sCursorWindowSize")
//            field.isAccessible = true
//            // Increase the CursorWindow size to 100MB (adjust as needed)
//            field.set(null, 100 * 1024 * 1024)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
}
