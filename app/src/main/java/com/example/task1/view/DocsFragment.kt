package com.example.task1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.R
import com.example.task1.databinding.FragmentImagesBinding

class DocsFragment : Fragment() {

    //    lateinit var userViewModel: UserListViewModel
    //    lateinit var userAdapter: UserListAdapter

    private lateinit var binding: FragmentImagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //   userViewModel = ViewModelProviders.of(activity!!).get(UserListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_docs,
            container, false
        )

        val recyclerView = binding.recyclerView

        //        userAdapter = UserListAdapter()
//        recyclerView.setAdapter(userAdapter)
//        userViewModel.getAllUsers().observe(this, object : Observer<List<User>> {
//            override fun onChanged(users: List<User>?) {
//                // Update the cached copy of the words in the adapter.
//                userAdapter.setListItems(users)
//            }
//        })

        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL, false
        )

        return binding.root
    }
}
