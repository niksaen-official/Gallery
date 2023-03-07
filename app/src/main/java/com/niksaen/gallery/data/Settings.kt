package com.niksaen.gallery.data

import android.content.Context
import android.content.SharedPreferences

class Settings(context: Context) {
    var notificationActive = false
    private var prefs:SharedPreferences
    init {
        prefs = context.getSharedPreferences("settings",Context.MODE_PRIVATE)
        notificationActive = prefs.getBoolean("notificationActive",false)
    }
    fun setNotificationActive(){
        notificationActive = !notificationActive
        prefs.edit().putBoolean("notificationActive",notificationActive).apply()
    }
}