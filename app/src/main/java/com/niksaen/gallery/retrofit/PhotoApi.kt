package com.niksaen.gallery.retrofit

import com.niksaen.gallery.models.retrofit.ResponseFlickr
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface PhotoApi {
    @GET("?method=flickr.photos.getRecent&api_key=63e35cee441f27c4d161d39892b0e98a&format=json&nojsoncallback=1")
    @Headers("Content-Type: application/json")
    fun getResponse(): Single<ResponseFlickr>
}