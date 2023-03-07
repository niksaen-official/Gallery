package com.niksaen.gallery

import android.Manifest
import android.app.*
import android.app.ActivityManager.RunningTaskInfo
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.niksaen.gallery.retrofit.PhotoApi
import com.niksaen.gallery.ui.MainActivity
import io.realm.Realm
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class GalleryApp:Application() {
    lateinit var photoApi:PhotoApi
    lateinit var notificationManager:NotificationManager
    val CHANNEL_ID = "CHANNEL"

    override fun onCreate() {
        super.onCreate()
        configureApi()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
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
    fun configureNotification():Notification{
        val builder = Notification.Builder(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID,"NAME", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
            builder.setChannelId(CHANNEL_ID)
        }
        builder.setSmallIcon(android.R.drawable.ic_dialog_info)
        builder.setContentTitle("Gallery")
        builder.setContentText("New photo")
        return builder.build()
    }
    fun sendNotification(){
        if (isNetworkAvailable(this)) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
                notificationManager.notify(1,configureNotification())
            } else {
                val permStat =
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
                        ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        PackageManager.PERMISSION_DENIED
                    }
                if(permStat == PackageManager.PERMISSION_GRANTED){
                    notificationManager.notify(1,configureNotification())
                }
            }
        }
    }
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }
}