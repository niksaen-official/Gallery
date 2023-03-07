package com.niksaen.gallery.models.retrofit

data class ResponseFlickr(
    var photos: Photos
)
data class Photos(
    var page:Int,
    var pages:Int,
    var perpage:Int,
    var total:Int,
    var photo:List<Photo>,
    var stat:String
)

data class Photo(
    var id:String,
    var owner:String,
    var secret:String,
    var server:String,
    var farm:String,
    var title:String,
    var ispublic:Int,
    var isfriend:Int,
    var isfamily:Int
)
