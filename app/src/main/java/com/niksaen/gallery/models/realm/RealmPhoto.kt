package com.niksaen.gallery.models.realm
import io.realm.RealmObject
import kotlin.properties.Delegates

open class RealmPhoto : RealmObject(){
    var id:String = ""
    var owner:String = ""
    var secret:String = ""
    var server:String = ""
    var farm:String = ""
    var title:String = ""
    var ispublic:Int = 0
    var isfriend:Int = 0
    var isfamily:Int = 0
}