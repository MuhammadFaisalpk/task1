package com.example.task1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.task1.model.Documents
import com.example.task1.model.Images
import com.example.task1.model.Videos
import com.example.task1.repository.Repository

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository(application)

    var allImages: LiveData<ArrayList<Images>> = repository.allLocalImages

    var allVideos: LiveData<ArrayList<Videos>> = repository.allLocalVideos

    var allDocs: LiveData<ArrayList<Documents>> = repository.allLocalDocs

}