package com.example.task1

import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.task1.databinding.ActivityVideoPlayerBinding

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoPlayerBinding
    lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        getSetData()
    }

    private fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player)

        videoView = binding.videoView
    }

    private fun getSetData() {
        val bundle: Bundle? = intent.extras
        val path: String? = bundle!!.getString("video_path")

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        mediaController.requestFocus()
        videoView.setMediaController(mediaController)
        videoView.setVideoPath(path)
        videoView.requestFocus()
        videoView.start()
    }

}