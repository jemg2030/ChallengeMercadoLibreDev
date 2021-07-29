package com.example.challengemercadolibredev.mvvmmecadolibre.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Search(
    val results : List<Result>
): Parcelable