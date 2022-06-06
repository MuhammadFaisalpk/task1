package com.example.task1

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.task1.adapter.ImagesPagerAdapter
import com.example.task1.databinding.ActivityImageSliderBinding
import com.example.task1.model.ImagesModel

class ImageSliderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageSliderBinding
    private lateinit var imagePager: ViewPager2
    private lateinit var previous: Button
    private lateinit var next: Button
    private var allImages: ArrayList<ImagesModel> = ArrayList()
    private var position: Int = 0
    lateinit var imagesPagerAdapter: ImagesPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_slider)

        initViews()
        getImagesData()
        setPagerAdapter()
        clickListeners()

    }


    private fun initViews() {
        previous = binding.previous
        next = binding.next
        imagePager = binding.imagePager
    }

    private fun getImagesData() {

        position = intent.getIntExtra("image_position", 0)
        allImages = intent?.getParcelableArrayListExtra("images_list")
            ?: throw IllegalStateException("Images array list is null")
    }

    private fun setPagerAdapter() {
        /**
         * setting up the viewPager with images
         */
        imagesPagerAdapter = ImagesPagerAdapter(this, allImages)
        imagePager.adapter = imagesPagerAdapter
        imagePager.offscreenPageLimit = 3
        imagePager.currentItem = position
    }

    private fun clickListeners() {
        previous.setOnClickListener() {
            imagePager.setCurrentItem(getItem(-1), true) //getItem(-1) for previous
        }
        next.setOnClickListener() {
            imagePager.setCurrentItem(getItem(+1), true) //getItem(-1) for previous
        }
    }

    private fun getItem(value: Int): Int {
        return imagePager.currentItem + value
    }
}