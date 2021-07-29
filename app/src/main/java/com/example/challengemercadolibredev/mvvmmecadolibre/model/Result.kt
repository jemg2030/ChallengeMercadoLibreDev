package com.example.challengemercadolibredev.mvvmmecadolibre.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result (
    val id : String,
    val title : String,
    val price: String,
    val thumbnail: String,
    val installments : Installments,
    val shipping: Shipping
): Parcelable