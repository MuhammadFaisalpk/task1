package com.example.task1.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.R

class DocsFragment : Fragment() {
    lateinit var listFragmentView: View

    //    lateinit var userViewModel: UserListViewModel
    lateinit var recyclerView: RecyclerView

    //    lateinit var userAdapter: UserListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        userViewModel = ViewModelProviders.of(activity!!).get(UserListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listFragmentView = inflater.inflate(R.layout.fragment_docs, container, false)
        initVars(listFragmentView)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

//        userAdapter = UserListAdapter()
//        recyclerView.setAdapter(userAdapter)
//        userViewModel.getAllUsers().observe(this, object : Observer<List<User>> {
//            override fun onChanged(users: List<User>?) {
//                // Update the cached copy of the words in the adapter.
//                userAdapter.setListItems(users)
//            }
//        })
        return listFragmentView
    }

    private fun setAdapter() {

    }

    private fun initVars(view: View) {
        recyclerView = view.findViewById(R.id.docs_list)
    }
}
