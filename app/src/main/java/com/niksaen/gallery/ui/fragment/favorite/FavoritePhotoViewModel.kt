package com.niksaen.gallery.ui.fragment.favorite

import androidx.lifecycle.ViewModel
import com.niksaen.gallery.models.realm.RealmPhoto
import io.realm.Realm

class FavoritePhotoViewModel : ViewModel() {
    private val realm = Realm.getDefaultInstance()
    var photo:List<RealmPhoto>
    init {
        photo = realm.where(RealmPhoto::class.java).findAll()
    }
    fun removePhoto(realmPhoto: RealmPhoto){
        realm.beginTransaction()
        val buff = realm.where(RealmPhoto::class.java).equalTo("id",realmPhoto.id).findAll()
        if(!buff.isEmpty()){
            for (photo in buff){
                photo.deleteFromRealm()
            }
        }
        realm.commitTransaction()
        photo = realm.where(RealmPhoto::class.java).findAll()
    }
}