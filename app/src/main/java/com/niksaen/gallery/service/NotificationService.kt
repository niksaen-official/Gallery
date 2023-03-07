package com.niksaen.gallery.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.niksaen.gallery.GalleryApp
import java.util.concurrent.TimeUnit

class NotificationService : Service() {
    var isActive = true

    override fun onCreate() {
        super.onCreate()
        someTask()
        Log.e("NotificationService","Service created and started")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        isActive = false
        Log.e("NotificationService","Service stopped and destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun someTask() {
        Thread {
            while (isActive) {
                Log.e("NotificationService","Service is running")
                (application as GalleryApp).sendNotification()
                try {
                    TimeUnit.SECONDS.sleep(5)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }
}