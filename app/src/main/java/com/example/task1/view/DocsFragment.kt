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
import com.example.task1.databinding.FragmentDocsBinding
import com.example.task1.databinding.FragmentImagesBinding
import com.example.task1.viewModel.viewModel

class DocsFragment : Fragment() {

    private lateinit var viewModal: viewModel
    private lateinit var imagesListAdapter: ImagesListAdapter
    private lateinit var binding: FragmentDocsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_docs,
            container, false
        )

        initViews()
        setupViewModel()

        return binding.root
    }

    private fun initViews() {

        val recyclerView = binding.recyclerView

        imagesListAdapter = ImagesListAdapter(this)
        recyclerView.adapter = imagesListAdapter

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

        viewModal.allDocs.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                //on below line we are updating our list.
                imagesListAdapter.setListItems(it)
            }
        })
    }
}
