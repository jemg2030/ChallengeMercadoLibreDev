package com.example.challengemercadolibredev.mvvmmecadolibre.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item (
    val title :String,
    val price : Price,
    val discount: Discount,
    val picture: PictureURL
): Parcelable