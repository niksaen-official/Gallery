package com.niksaen.gallery

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.niksaen.gallery.data.Settings
import com.niksaen.gallery.retrofit.PhotoApi
import com.niksaen.gallery.service.NotificationService
import io.realm.Realm
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class GalleryApp:Application() {
    lateinit var photoApi:PhotoApi
    val CHANNEL_ID = "CHANNEL"


    override fun onCreate() {
        super.onCreate()
        configureApi()

        Realm.init(this)
    }
    private fun configureApi(){
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.flickr.com/services/rest/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        photoApi = retrofit.create(PhotoApi::class.java)
    }
    fun sendNotification(){
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID,"NAME", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val builder = Notification.Builder(this)
        builder.setSmallIcon(android.R.drawable.ic_dialog_info)
        builder.setContentTitle("Gallery")
        builder.setContentText("New photo")
        builder.setChannelId(CHANNEL_ID)
        notificationManager.notify(1, builder.build())
    }
}