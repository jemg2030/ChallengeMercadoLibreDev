package com.example.challengemercadolibredev.mvvmmecadolibre.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailProduct (
    val title :String,
    val price : Long,
    val initial_quantity: Int,
    val available_quantity: Int,
    val condition: String,
    val pictures: List<PictureURL>,
    val shipping : Shipping
): Parcelable