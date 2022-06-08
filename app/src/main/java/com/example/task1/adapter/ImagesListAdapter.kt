package com.example.task1.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.task1.ImageSliderActivity
import com.example.task1.R
import com.example.task1.model.Images
import com.example.task1.utils.Helper
import com.google.android.material.color.MaterialColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File


class ImagesListAdapter(private val context: Fragment) :
    RecyclerView.Adapter<ImagesListAdapter.ViewHolder>() {

    var items: ArrayList<Images>? = null
    private var newPosition = 0

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.images_list_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            newPosition = position

            holder.nameHolder.text = items?.get(position)?.title
            holder.fnameHolder.text = items?.get(position)?.folderName

            val imagePath: String? = items?.get(position)?.path
            Glide.with(context)
                .load(imagePath)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.imageHolder)

            holder.itemView.setOnClickListener() {
                val intent = Intent(it.context, ImageSliderActivity::class.java)
                intent.putExtra("image_position", position)
                intent.putExtra("images_list", items)
                it.context.startActivity(intent)
            }

            holder.optionHolder.setOnClickListener() {
                val popupMenu = PopupMenu(it.context, holder.optionHolder)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_rename -> requestWriteR()
                        R.id.action_delete -> requestDeleteR(position = position)

                    }
                    true
                }
                popupMenu.show()
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

    private fun requestWriteR() {
        //files to modify
        val uriList: List<Uri> = listOf(
            Uri.withAppendedPath(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                items?.get(newPosition)?.id
            )
        )
        //requesting file write permission for specific files
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val pi =
                MediaStore.createWriteRequest(context.requireContext().contentResolver, uriList)
            (context.context as Activity).startIntentSenderForResult(
                pi.intentSender, 124,
                null, 0, 0, 0, null
            )
        } else renameFunction(newPosition)
    }

    private fun renameFunction(position: Int) {
        val dialog = context.context?.let { Dialog(it) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog.setCancelable(true)

            dialog.setContentView(R.layout.rename_dialog_design)
            val name = dialog.findViewById(R.id.name) as EditText
            val ok = dialog.findViewById(R.id.ok) as Button
            ok.setOnClickListener {
                val newName = name.text.toString()
                if (newName.isNotEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val currentFile = items?.get(position)?.path?.let { File(it) }
                        if (currentFile != null) {
                            if (currentFile.exists() && newName.toString()
                                    .isNotEmpty()
                            ) {
                                val newFile = File(
                                    currentFile.parentFile,
                                    newName.toString() + "." + currentFile.extension
                                )

                                val fromUri = Uri.withAppendedPath(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    items?.get(position)?.id
                                )

                                ContentValues().also {
                                    it.put(MediaStore.Files.FileColumns.IS_PENDING, 1)
                                    context.requireContext().contentResolver.update(
                                        fromUri,
                                        it,
                                        null,
                                        null
                                    )
                                    it.clear()
                                    //updating file details
                                    it.put(
                                        MediaStore.Files.FileColumns.DISPLAY_NAME,
                                        newName.toString()
                                    )
                                    it.put(MediaStore.Files.FileColumns.IS_PENDING, 0)
                                    context.requireContext().contentResolver.update(
                                        fromUri,
                                        it,
                                        null,
                                        null
                                    )
                                }

//                            updateRenameUI(
//                                position,
//                                newName = newName.toString(),
//                                newFile = newFile
//                            )
                            }
                        }
                        dialog.dismiss()
                    } else {
                        val currentFile = items?.get(position)?.path?.let { it1 -> File(it1) }
                        if (currentFile != null) {
                            if (currentFile.exists() && newName.toString()
                                    .isNotEmpty()
                            ) {
                                val newFile = File(
                                    currentFile.parentFile,
                                    newName.toString() + "." + currentFile.extension
                                )
                                if (currentFile.renameTo(newFile)) {
                                    MediaScannerConnection.scanFile(
                                        context.context,
                                        arrayOf(newFile.toString()),
                                        arrayOf("video/*"),
                                        null
                                    )
                                    //                            updateRenameUI(
                                    //                                position = position,
                                    //                                newName = newName.toString(),
                                    //                                newFile = newFile
                                    //                            )
                                }
                            }
                        }
                        dialog.dismiss()
                    }
                } else {
                    name.error = "Field required."
                }
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private fun requestDeleteR(position: Int) {
        //list of images to delete
        val uriList: List<Uri> = listOf(
            Uri.withAppendedPath(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                items?.get(position)?.id
            )
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //requesting for delete permission
            val pi =
                MediaStore.createDeleteRequest(context.requireContext().contentResolver, uriList)
            (context.requireContext() as Activity).startIntentSenderForResult(
                pi.intentSender, 123,
                null, 0, 0, 0, null
            )
        } else {
            //for devices less than android 11
            val file = items?.get(position)?.path?.let { File(it) }
            val builder = MaterialAlertDialogBuilder(context.requireContext())
            builder.setTitle("Delete Image?")
                .setMessage(items?.get(position)?.title)
                .setPositiveButton("Yes") { self, _ ->
                    if (file != null) {
                        if (file.exists() && file.delete()) {
                            MediaScannerConnection.scanFile(
                                context.requireContext(),
                                arrayOf(file.path),
                                null,
                                null
                            )
                        }
                    }
                    self.dismiss()
                }
                .setNegativeButton("No") { self, _ -> self.dismiss() }
            val delDialog = builder.create()
            delDialog.show()
        }
    }

    fun setListItems(items: ArrayList<Images>) {
        this.items = items
        notifyDataSetChanged()
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageHolder: ImageView = itemView.findViewById(R.id.imageView)
        val optionHolder: ImageView = itemView.findViewById(R.id.option)
        val nameHolder: TextView = itemView.findViewById(R.id.name)
        val fnameHolder: TextView = itemView.findViewById(R.id.foldername)
    }
}