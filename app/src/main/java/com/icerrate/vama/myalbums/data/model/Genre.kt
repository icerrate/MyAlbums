package com.icerrate.vama.myalbums.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    val genreId: String,
    val name: String,
    val url: String
) : Parcelable
