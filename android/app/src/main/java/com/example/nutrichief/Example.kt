package com.example.nutrichief

import android.os.Parcel
import android.os.Parcelable

data class Example (
    val user_id: Int,
    val food_id: Int,
    val item_qty: Int,
    val food_name: String,
    val food_price: String? = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(user_id)
        parcel.writeInt(food_id)
        parcel.writeInt(item_qty)
        parcel.writeString(food_name)
        parcel.writeString(food_price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Example> {
        override fun createFromParcel(parcel: Parcel): Example {
            return Example(parcel)
        }

        override fun newArray(size: Int): Array<Example?> {
            return arrayOfNulls(size)
        }
    }

}
