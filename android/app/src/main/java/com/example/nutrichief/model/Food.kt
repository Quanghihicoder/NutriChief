package com.example.nutrichief.model

import android.os.Parcel
import android.os.Parcelable

data class Food(
    val foodId: Int,
    val foodName: String,
    val foodDesc: String,
    val foodCTime: Int,
    val foodPTime: Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(foodId)
        parcel.writeString(foodName)
        parcel.writeString(foodDesc)
        parcel.writeInt(foodCTime)
        parcel.writeInt(foodPTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Food> {
        override fun createFromParcel(parcel: Parcel): Food {
            return Food(parcel)
        }

        override fun newArray(size: Int): Array<Food?> {
            return arrayOfNulls(size)
        }
    }
}


