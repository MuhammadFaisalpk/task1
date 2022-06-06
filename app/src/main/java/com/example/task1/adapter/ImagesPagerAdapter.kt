package com.example.task1.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.task1.R
import com.example.task1.model.ImagesModel

class ImagesPagerAdapter(
    private val context: Activity,
    private val allImages: ArrayList<ImagesModel>
) :
    RecyclerView.Adapter<ImagesPagerAdapter.ViewHolder>() {
    // Array of images
    // Adding images from drawable folder

    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.image_slider_layout, parent, false)
        return ViewHolder(view)
    }

    // This method binds the screen with the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        ViewCompat.setTransitionName( holder.images, position.toString() + "picture")
        val pic: ImagesModel = allImages.get(position)
        Glide.with(context)
            .load(pic.path)
            .apply(RequestOptions().fitCenter())
            .into( holder.images)
        holder.images.setOnClickListener {
            Toast.makeText(context, pic.name, Toast.LENGTH_SHORT).show()
        }
    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return allImages.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var images: ImageView

        init {
            images = itemView.findViewById(R.id.image)
        }
    }
}