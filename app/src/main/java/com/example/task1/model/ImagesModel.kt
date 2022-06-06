package com.example.task1.model

import android.os.Parcel
import android.os.Parcelable

data class ImagesModel(
    var name: String? = null,
    var path: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImagesModel> {
        override fun createFromParcel(parcel: Parcel): ImagesModel {
            return ImagesModel(parcel)
        }

        override fun newArray(size: Int): Array<ImagesModel?> {
            return arrayOfNulls(size)
        }
    }

}