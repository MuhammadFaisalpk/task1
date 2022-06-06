package com.example.task1.utils

import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.task1.R
import com.example.task1.model.ImagesModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File

class Helper(val context: Context) : Interfaces {

    fun deleteItem(imageModel: ImagesModel) {
        //list of images to delete
        val uriList: List<Uri> = listOf(
            Uri.withAppendedPath(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                imageModel.id
            )
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //requesting for delete permission
            val pi = MediaStore.createDeleteRequest(context.contentResolver, uriList)
            (context as Activity).startIntentSenderForResult(
                pi.intentSender, 123,
                null, 0, 0, 0, null
            )
        } else {
            //for devices less than android 11
            val file = File(imageModel.path)
            val builder = MaterialAlertDialogBuilder(context)
            builder.setTitle("Delete Image?")
                .setMessage(imageModel.name)
                .setPositiveButton("Yes") { self, _ ->
                    if (file.exists() && file.delete()) {
                        MediaScannerConnection.scanFile(context, arrayOf(file.path), null, null)
                    }
                    self.dismiss()
                }
                .setNegativeButton("No") { self, _ -> self.dismiss() }
            val delDialog = builder.create()
            delDialog.show()
        }
    }

    fun requestWriteR(imagesModel: ImagesModel) {
        //files to modify
        val uriList: List<Uri> = listOf(
            Uri.withAppendedPath(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                imagesModel.id
            )
        )

        //requesting file write permission for specific files
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val pi = MediaStore.createWriteRequest(context.contentResolver, uriList)
            (context as Activity).startIntentSenderForResult(
                pi.intentSender, 124,
                null, 0, 0, 0, null
            )
        } else renameDialog(imagesModel)
    }

    private fun renameDialog(imagesModel: ImagesModel) {
        val id = imagesModel.id
        val path = imagesModel.path

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.rename_dialog_design)
        val name = dialog.findViewById(R.id.name) as EditText
        val ok = dialog.findViewById(R.id.ok) as Button
        ok.setOnClickListener {
            val newName = name.text.toString()
            if (newName.isNotEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val currentFile = File(path.toString())
                    if (currentFile.exists() && newName.toString()
                            .isNotEmpty()
                    ) {
                        val newFile = File(
                            currentFile.parentFile,
                            newName + "." + currentFile.extension
                        )

                        val fromUri = Uri.withAppendedPath(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            id
                        )

                        ContentValues().also {
                            it.put(MediaStore.Files.FileColumns.IS_PENDING, 1)
                            context.contentResolver.update(fromUri, it, null, null)
                            it.clear()

                            //updating file details
                            it.put(MediaStore.Files.FileColumns.DISPLAY_NAME, newName.toString())
                            it.put(MediaStore.Files.FileColumns.IS_PENDING, 0)
                            context.contentResolver.update(fromUri, it, null, null)
                        }
                    }
                } else {
                    val currentFile = File(path.toString())
                    if (currentFile.exists() && newName.isNotEmpty()) {
                        val newFile = File(
                            currentFile.parentFile,
                            newName + "." + currentFile.extension
                        )
                        if (currentFile.renameTo(newFile)) {
                            MediaScannerConnection.scanFile(
                                context,
                                arrayOf(newFile.toString()),
                                arrayOf("image/*"),
                                null
                            )
                        }
                    }
                }
            } else {
                name.error = "Field required."
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onIntent(i: Intent?, resultCode: Int) {
        when (resultCode) {
            200 -> if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(context, "yes", Toast.LENGTH_SHORT).show()
//                val imagesModel = ImagesModel()
//                renameDialog(newPosition)
            }
        }
    }

    override fun onIntent1(i: Intent?, resultCode: Int) {
        when (resultCode) {
            124 -> if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(context, "no", Toast.LENGTH_SHORT).show()
//                val imagesModel = ImagesModel()
//                renameDialog(newPosition)
            }
        }
    }
}

