package com.example.task1.view

import android.app.Activity
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.R
import com.example.task1.adapter.ImagesListAdapter
import com.example.task1.databinding.FragmentImagesBinding
import com.example.task1.model.ImagesModel
import com.example.task1.viewModel.ImagesListViewModel
import com.example.task1.viewModel.vmClass
import kotlinx.coroutines.flow.collectLatest


class ImagesFragment : Fragment() {

    private val imagesListViewModel: ImagesListViewModel by activityViewModels()
    private val viewModel: vmClass by activityViewModels()

    //    lateinit var imagesListViewModel: ImagesListViewModel
    lateinit var imagesListAdapter: ImagesListAdapter

    private lateinit var binding: FragmentImagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        imagesListViewModel = ViewModelProviders.of(activity!!).get(ImagesListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_images,
            container, false
        )

        viewModel.loadAllImages()

        val recyclerView = binding.recyclerView

        imagesListAdapter = ImagesListAdapter(this)
        recyclerView.adapter = imagesListAdapter

        val list: ArrayList<String> = fetchGalleryImages(this@ImagesFragment.context as Activity)

        imagesListAdapter.setListItems(list)

        Log.e("Tagged", list.size.toString())

        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL, false
        )

        return binding.root
    }

    private fun fetchGalleryImages(context: Activity): ArrayList<String> {
        val columns = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media._ID
        ) //get all columns of type images
        val orderBy = MediaStore.Images.Media.DATE_TAKEN //order data by date
        val imageCursor: Cursor = context.managedQuery(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
            null, "$orderBy DESC"
        ) //get all data in Cursor by sorting in DESC order
        val galleryImageUrls: ArrayList<String> = ArrayList()
        for (i in 0 until imageCursor.count) {
            imageCursor.moveToPosition(i)
            val dataColumnIndex: Int =
                imageCursor.getColumnIndex(MediaStore.Images.Media.DATA) //get column index
            galleryImageUrls.add(imageCursor.getString(dataColumnIndex)) //get Image from column index
        }

        return galleryImageUrls
    }
}
