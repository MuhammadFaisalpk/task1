package com.example.task1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.task1.model.ImagesModel
import com.example.task1.repository.Repository

class viewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository(application)

    var allImages: LiveData<ArrayList<ImagesModel>> = repository.allLocalImages

    var allVideos: LiveData<ArrayList<ImagesModel>> = repository.allLocalVideos

    var allDocs: LiveData<ArrayList<ImagesModel>> = repository.allLocalDocs

}