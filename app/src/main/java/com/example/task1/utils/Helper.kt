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
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.task1.R
import com.example.task1.model.Images
import com.example.task1.model.Videos
import com.example.task1.view.ImagesFragment
import com.example.task1.view.VideosFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File

class Helper(val context: Context) : DeleteInterface {

    fun onIntent(i: Intent?, resultCode: Int, requestCode: Int) {
        when (requestCode) {
            123 -> if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT).show()
            }
            124 -> if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(context, "Rename.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun requestDeleteR(v: View?, item: Videos) {
        //list of videos to delete
        val uriList: List<Uri> = listOf(
            Uri.withAppendedPath(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                item.id
            )
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //requesting for delete permission
            if (v != null) {
                val pi =
                    MediaStore.createDeleteRequest(v.context.contentResolver, uriList)

                (v.context as Activity).startIntentSenderForResult(
                    pi.intentSender, 123,
                    null, 0, 0, 0, null
                )
            }
        } else {
            //for devices less than android 11
            if (v != null) {
                val file = item.path?.let { File(it) }
                val builder = MaterialAlertDialogBuilder(v.context)
                builder.setTitle("Delete Video?")
                    .setMessage(item.title)
                    .setPositiveButton("Yes") { self, _ ->
                        if (file != null) {
                            if (file.exists() && file.delete()) {
                                MediaScannerConnection.scanFile(
                                    v.context,
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
    }

//    override fun requestDeleteR(v: View?, video: Videos, position: Int) {
//        TODO("Not yet implemented")
//    }

    override fun afterDeleteRefresh() {
        TODO("Not yet implemented")
    }
}

