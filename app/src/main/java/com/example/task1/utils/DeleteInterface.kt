package com.example.task1.utils

import android.view.View
import com.example.task1.model.Videos

interface DeleteInterface {
    fun requestDeleteR(v: View?, video: Videos)
    fun afterDeleteRefresh()
}