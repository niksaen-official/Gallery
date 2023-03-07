package com.niksaen.gallery.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import com.niksaen.gallery.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val permStat = ContextCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS)
        if(permStat == PackageManager.PERMISSION_GRANTED){
           nextActivity()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS),0)
            nextActivity()
        }
    }
    fun nextActivity(){
        val intent = Intent(this,GalleryActivity::class.java)
        startActivity(intent)
        finish()
    }
}