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
import com.example.task1.adapter.DocsListAdapter
import com.example.task1.databinding.FragmentDocsBinding
import com.example.task1.viewModel.ViewModel

class DocsFragment : Fragment() {

    private lateinit var viewModal: ViewModel
    private lateinit var docsListAdapter: DocsListAdapter
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

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        getAllItems()
    }

    private fun initViews() {

        val recyclerView = binding.recyclerView

        docsListAdapter = DocsListAdapter(this)
        recyclerView.adapter = docsListAdapter

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

        viewModal.allDocs.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                //on below line we are updating our list.
                docsListAdapter.setListItems(it)
            }
        })
    }
}
