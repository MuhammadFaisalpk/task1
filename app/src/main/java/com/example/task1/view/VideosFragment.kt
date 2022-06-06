package com.example.task1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.R
import com.example.task1.adapter.ImagesListAdapter
import com.example.task1.adapter.VideosListAdapter
import com.example.task1.databinding.FragmentVideosBinding
import com.example.task1.viewModel.viewModel

class VideosFragment : Fragment() {

    private lateinit var viewModal: viewModel
    lateinit var videosListAdapter: VideosListAdapter
    private lateinit var binding: FragmentVideosBinding

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
        setupViewModel()

        return binding.root
    }

    private fun initViews() {

        val recyclerView = binding.recyclerView

        videosListAdapter = VideosListAdapter(this)
        recyclerView.adapter = videosListAdapter

        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL, false
        )
    }

    private fun setupViewModel() {
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity?.application!!)
        ).get(viewModel::class.java)

        viewModal.allVideos.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                //on below line we are updating our list.
                videosListAdapter.setListItems(it)
            }
        })
    }
}
