package com.example.task1.repository

import android.app.Application
import android.database.Cursor
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task1.model.ImagesModel
import java.io.File

class Repository(private val application: Application) {

    val allLocalImages: LiveData<ArrayList<ImagesModel>> = fetchAllImages()

    val allLocalVideos: LiveData<ArrayList<ImagesModel>> = fetchAllVideos()

    val allLocalDocs: LiveData<ArrayList<ImagesModel>> = fetchAllDocs()

    private fun fetchAllImages(): LiveData<ArrayList<ImagesModel>> {
        val listImages: ArrayList<ImagesModel> = ArrayList()
        val mutableListImages = MutableLiveData<ArrayList<ImagesModel>>()

        val columns = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME
        ) //get all columns of type images
        val orderBy = MediaStore.Images.Media.DATE_TAKEN //order data by date
        val cursor: Cursor? = application.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
            null, "$orderBy DESC"
        ) //get all data in Cursor by sorting in DESC order

        if (cursor != null) {
            for (i in 0 until cursor.count) {
                cursor.moveToPosition(i)
                val dataColumnID: Int =
                    cursor.getColumnIndex(MediaStore.Images.Media._ID) //get column index
                val dataColumnData: Int =
                    cursor.getColumnIndex(MediaStore.Images.Media.DATA) //get column index
                val dataColumnName: Int =
                    cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME) //get column index

                val id: String = cursor.getString(dataColumnID)
                val name: String = cursor.getString(dataColumnName)
                val path: String = cursor.getString(dataColumnData)


                listImages.add(
                    ImagesModel(
                        id,
                        name,
                        path
                    )
                ) //get Image from column index
            }
            cursor.close()
        }
        mutableListImages.value = listImages
        return mutableListImages
    }

    private fun fetchAllVideos(): LiveData<ArrayList<ImagesModel>> {
        val listVideos: ArrayList<ImagesModel> = ArrayList()
        val mutableListVideos = MutableLiveData<ArrayList<ImagesModel>>()

        val columns = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME
        ) //get all columns of type images
        val orderBy = MediaStore.Video.Media.DATE_TAKEN //order data by date
        val cursor: Cursor? = application.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null,
            null, "$orderBy DESC"
        ) //get all data in Cursor by sorting in DESC order

        if (cursor != null) {
            for (i in 0 until cursor.count) {
                cursor.moveToPosition(i)
                val dataColumnID: Int =
                    cursor.getColumnIndex(MediaStore.Images.Media._ID) //get column index
                val dataColumnData: Int =
                    cursor.getColumnIndex(MediaStore.Video.Media.DATA) //get column index
                val dataColumnName: Int =
                    cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME) //get column index

                val id: String = cursor.getString(dataColumnID)
                val name: String = cursor.getString(dataColumnName)
                val path: String = cursor.getString(dataColumnData)

                listVideos.add(
                    ImagesModel(
                        id,
                        name,
                        path
                    )
                ) //get Image from column index
            }
            cursor.close()
        }
        mutableListVideos.value = listVideos
        return mutableListVideos
    }

    private fun fetchAllDocs(): LiveData<ArrayList<ImagesModel>> {
        val listDocs: ArrayList<ImagesModel> = ArrayList()
        val mutableListDocs = MutableLiveData<ArrayList<ImagesModel>>()

        var gpath: String = Environment.getExternalStorageDirectory().absolutePath
        var fullpath = File(gpath + File.separator + "Download")

        val fileList: ArrayList<File> = ArrayList()
        val listAllFiles = fullpath.listFiles()

        if (listAllFiles != null && listAllFiles.isNotEmpty()) {
            for (currentFile in listAllFiles) {
//                if (currentFile.name.endsWith(".pdf")) {
                // File absolute path
                Log.e("downloadFilePath", currentFile.absolutePath)
                // File Name
                Log.e("downloadFileName", currentFile.name)
                fileList.add(currentFile.absoluteFile)
//                }
            }
            Log.w("fileList", "" + fileList.size)
        }

        return mutableListDocs
    }
}