package com.example.challengemercadolibredev.mvvmmecadolibre.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Component (
    val type :String,
    val elements : List<Element>,
    val items: List<Item>
): Parcelable