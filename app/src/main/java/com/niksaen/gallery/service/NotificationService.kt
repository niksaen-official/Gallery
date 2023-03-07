package com.niksaen.gallery.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.niksaen.gallery.GalleryApp
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NotificationService : Service() {
    var isActive = true
    var oldPhotoUrl = ""
    private val disposable = CompositeDisposable()

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
        disposable.dispose()
        Log.e("NotificationService","Service stopped and destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("CheckResult")
    fun someTask() {
        Observable.timer(5,TimeUnit.SECONDS)
            .subscribe {
                (application as GalleryApp).photoApi.getResponse()
                    .map { it.photos.photo }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe( {
                        val newPhotoUrl = "https://live.staticflickr.com/"+it[0].server+"/"+it[0].id+"_"+it[0].secret+".jpg"
                        Log.e("Tag","\nnew url: $newPhotoUrl\nold url: $oldPhotoUrl")
                        if(oldPhotoUrl != newPhotoUrl){
                            (application as GalleryApp).sendNotification()
                            oldPhotoUrl = newPhotoUrl
                            Log.e("Tag","\nnew url: $newPhotoUrl\nold url: $oldPhotoUrl")
                        }
                    },{})
            }.addTo(disposable)
    }
}