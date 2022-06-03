package com.example.task1.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.R

class VideosFragment : Fragment() {

    lateinit var rootView: View
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_videos, container, false)

        initVars(rootView)

        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        return rootView
    }

    private fun initVars(view: View) {
        recyclerView = view.findViewById(R.id.videos_list)
    }
}
