package com.example.task1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.R
import com.example.task1.adapter.VideosListAdapter
import com.example.task1.databinding.FragmentVideosBinding
import com.example.task1.viewModel.ViewModel


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
        getAllItems()

        return binding.root
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
}
