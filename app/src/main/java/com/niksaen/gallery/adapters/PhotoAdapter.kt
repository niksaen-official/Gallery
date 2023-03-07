package com.niksaen.gallery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.niksaen.gallery.R
import com.niksaen.gallery.models.retrofit.Photo
import com.squareup.picasso.Picasso

class PhotoAdapter(private val context: Context?, private val photos:List<Photo>) : RecyclerView.Adapter<PhotoAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return photos.count()
    }
    fun getItem(position: Int) : Photo{ return photos[position]}
    override fun onBindViewHolder(holder: Holder, position: Int) {
        Picasso.with(context)
            .load("https://live.staticflickr.com/"+photos[position].server+"/"+photos[position].id+"_"+photos[position].secret+".jpg")
            .into(holder.imageView)
    }
    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val imageView:ImageView
        init {
            imageView = view as ImageView
        }
    }
}
