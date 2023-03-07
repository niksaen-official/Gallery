package com.niksaen.gallery.ui.fragment.main

import androidx.lifecycle.ViewModel
import com.niksaen.gallery.models.retrofit.Photo
import com.niksaen.gallery.retrofit.PhotoApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmConfiguration

class MainViewModel : ViewModel() {
    private val realm:Realm
    init {
        val configure = RealmConfiguration.Builder()
        configure.allowWritesOnUiThread(true)
        realm = Realm.getInstance(configure.build())
    }
    fun getPhotoList(photoApi: PhotoApi):Single<List<Photo>>{
        return photoApi.getResponse()
            .map { it.photos.photo }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun addPhotoToFavorite(photo: Photo){
        realm.beginTransaction()
        val _photo = realm.createObject(com.niksaen.gallery.models.realm.RealmPhoto::class.java)
        _photo.id = photo.id
        _photo.owner = photo.owner
        _photo.server = photo.server
        _photo.secret = photo.secret
        _photo.farm = photo.farm
        _photo.title = photo.title
        _photo.ispublic = photo.ispublic
        _photo.isfriend = photo.isfriend
        _photo.isfamily = photo.isfamily
        realm.commitTransaction()
    }
}