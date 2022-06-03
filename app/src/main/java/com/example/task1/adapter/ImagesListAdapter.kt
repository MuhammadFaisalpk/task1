package com.example.task1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.task1.R
import com.example.task1.model.ImagesModel
import com.example.task1.view.ImagesFragment

class ImagesListAdapter(private val context: ImagesFragment) :
    RecyclerView.Adapter<ImagesListAdapter.ViewHolder>() {

    var items: List<String>? = null

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.images_list_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {

            val folderName: String? = items?.get(position)
//            holder.nameHolder.text = folderName
//            holder.typeHolder.text = folderName
//
//            val fileName: String? = items?.get(position)?.al_imagepath?.get(0)
            Glide.with(context)
                .load("file://$folderName")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.imageHolder)

            holder.optionHolder.setOnClickListener() {

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return if (items != null) {
            items!!.size
        } else {
            0
        }
    }

    public fun setListItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageHolder: ImageView = itemView.findViewById(R.id.imageView)
        val optionHolder: ImageView = itemView.findViewById(R.id.option)
        val nameHolder: TextView = itemView.findViewById(R.id.name)
        val typeHolder: TextView = itemView.findViewById(R.id.type)
    }
}