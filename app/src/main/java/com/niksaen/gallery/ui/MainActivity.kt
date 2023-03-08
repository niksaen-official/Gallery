package com.niksaen.gallery.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import com.niksaen.gallery.GalleryApp
import com.niksaen.gallery.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if((application as GalleryApp).isNetworkAvailable(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS),0)
                val permStat = ContextCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS)
                if(permStat == PackageManager.PERMISSION_GRANTED) nextActivity()
            }else{
                nextActivity()
            }
        }else{
            (application as GalleryApp).dialog("The application requires internet without it you will not receive new photos and notifications",this)
        }
    }
    fun nextActivity(){
        val runnable = Runnable {
            val intent = Intent(this,GalleryActivity::class.java)
            startActivity(intent)
            finish()
        }
        val handler = Handler()
        handler.postDelayed(runnable,3000)
        handler.removeCallbacks(runnable)
    }
}