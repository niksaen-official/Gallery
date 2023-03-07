package com.niksaen.gallery.ui.fragment.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.material.snackbar.Snackbar
import com.niksaen.gallery.R
import com.niksaen.gallery.adapters.PhotoAdapter
import com.niksaen.gallery.adapters.RealmPhotoAdapter
import com.niksaen.gallery.databinding.FragmentFavoritePhotoBinding
import com.niksaen.gallery.databinding.FragmentMainBinding
import com.niksaen.libs.ItemClickSupport

class FavoritePhotoFragment : Fragment() {

    companion object { fun newInstance() = FavoritePhotoFragment() }

    private lateinit var viewModel: FavoritePhotoViewModel
    private lateinit var ui: FragmentFavoritePhotoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = FragmentFavoritePhotoBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[FavoritePhotoViewModel::class.java]

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(ui.photoList)
        val adapter = RealmPhotoAdapter(context,viewModel.photo)
        ui.photoList.adapter = adapter
        ItemClickSupport.addTo(ui.photoList).setOnItemClickListener { recyclerView, position, v ->
            viewModel.removePhoto(adapter.getItem(position))
            adapter.notifyDataSetChanged()
            Snackbar.make(v, "Remove photo from favorite", Snackbar.LENGTH_LONG).show()
        }
        return ui.root
    }
}