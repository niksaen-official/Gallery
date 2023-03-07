package com.niksaen.gallery.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.niksaen.gallery.GalleryApp
import com.niksaen.gallery.service.NotificationService
import com.niksaen.gallery.R
import com.niksaen.gallery.data.Settings
import com.niksaen.gallery.databinding.ActivityGalleryBinding
import com.niksaen.gallery.ui.fragment.favorite.FavoritePhotoFragment
import com.niksaen.gallery.ui.fragment.main.MainFragment


class GalleryActivity : AppCompatActivity() {

    lateinit var ui:ActivityGalleryBinding
    lateinit var settings: Settings
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityGalleryBinding.inflate(layoutInflater)
        settings = Settings(this)
        setContentView(ui.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
            ui.mainTitle.text = "Home"
        }
        ui.goFavorite.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FavoritePhotoFragment.newInstance())
                .commitNow()
            ui.mainTitle.text = "Favorite"
        }
        ui.goMain.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,MainFragment.newInstance())
                .commitNow()
            ui.mainTitle.text = "Home"
        }
        ui.notification.setOnClickListener { notification() }
    }
    fun notification(){
        val intent = Intent(this,NotificationService::class.java)
        if (!settings.notificationActive){
            startService(intent)
            Snackbar.make(ui.root,"Notification activated", Snackbar.LENGTH_LONG).show()
            settings.setNotificationActive()
        }else{
            stopService(intent)
            Snackbar.make(ui.root,"Notification deactivated", Snackbar.LENGTH_LONG).show()
            settings.setNotificationActive()
        }
    }
}