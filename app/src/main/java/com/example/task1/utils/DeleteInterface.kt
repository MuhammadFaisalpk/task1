package com.example.task1.utils

import android.view.View
import com.example.task1.model.Images
import com.example.task1.model.Videos

interface DeleteInterface {
    fun requestDeleteR(v: View?, video: Videos)
    fun requestWriteR(v: View?, video: Videos, position: Int)
}