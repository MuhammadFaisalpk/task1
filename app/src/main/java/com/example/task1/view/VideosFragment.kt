package com.example.task1.view

import android.app.Activity
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.R
import com.example.task1.adapter.VideosListAdapter
import com.example.task1.databinding.FragmentVideosBinding
import com.example.task1.model.Videos
import com.example.task1.utils.DeleteInterface
import com.example.task1.viewModel.ViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File

class VideosFragment : Fragment() {

    private lateinit var viewModal: ViewModel
    lateinit var videosListAdapter: VideosListAdapter
    private lateinit var binding: FragmentVideosBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_videos,
            container, false
        )

        initViews()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        getAllItems()
    }

    private fun initViews() {

        recyclerView = binding.recyclerView

        videosListAdapter = VideosListAdapter(this)
        recyclerView.adapter = videosListAdapter

        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL, false
        )
    }

    private fun getAllItems() {
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity?.application!!)
        ).get(ViewModel::class.java)

        viewModal.allVideos.observe(viewLifecycleOwner) { videos ->
            if (videos.size > 0) {
                videosListAdapter.setListItems(videos)
            }
        }

    }

//    override fun requestDeleteR(v: View?, item: Videos, position: Int) {
//        //list of videos to delete
//        val uriList: List<Uri> = listOf(
//            Uri.withAppendedPath(
//                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                item.id
//            )
//        )
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            //requesting for delete permission
//            if (v != null) {
//                val pi =
//                    MediaStore.createDeleteRequest(v.context.contentResolver, uriList)
//
//                (v.context as Activity).startIntentSenderForResult(
//                    pi.intentSender, 123,
//                    null, 0, 0, 0, null
//                )
//            }
//        } else {
//            //for devices less than android 11
//            if (v != null) {
//                val file = item.path?.let { File(it) }
//                val builder = MaterialAlertDialogBuilder(v.context)
//                builder.setTitle("Delete Video?")
//                    .setMessage(item.title)
//                    .setPositiveButton("Yes") { self, _ ->
//                        if (file != null) {
//                            if (file.exists() && file.delete()) {
//                                MediaScannerConnection.scanFile(
//                                    v.context,
//                                    arrayOf(file.path),
//                                    null,
//                                    null
//                                )
//                            }
//
//                        }
//                        self.dismiss()
//                    }
//                    .setNegativeButton("No") { self, _ -> self.dismiss() }
//                val delDialog = builder.create()
//                delDialog.show()
//            }
//        }
//    }
//
//    override fun afterDeleteRefresh() {
//        getAllItems()
//    }

}
