package com.niksaen.gallery.ui.fragment.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.material.snackbar.Snackbar
import com.niksaen.gallery.GalleryApp
import com.niksaen.gallery.adapters.PhotoAdapter
import com.niksaen.gallery.databinding.FragmentMainBinding
import com.niksaen.libs.ItemClickSupport

class MainFragment : Fragment() {

    companion object { fun newInstance() = MainFragment() }

    private lateinit var viewModel: MainViewModel
    private lateinit var ui:FragmentMainBinding
    private lateinit var adapter:PhotoAdapter

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        ui = FragmentMainBinding.inflate(layoutInflater)
        viewModel.getPhotoList((activity?.application as GalleryApp).photoApi)
            .subscribe({
                adapter = PhotoAdapter(context,it)
                ui.photoList.adapter = adapter
            },{
                Log.e("Error!!!",it.message.toString())
            })
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(ui.photoList)
        ItemClickSupport.addTo(ui.photoList).setOnItemClickListener { _, position, v ->
            viewModel.addPhotoToFavorite(adapter.getItem(position))
            Snackbar.make(v,"Add to favorite",Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ui.root
    }

}