package com.omellete.calminapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artikel(
    var judul: String,
    var penulis: String,
    var sumber: String,
    var gambar: Int,
    var isiartikel: String,
) : Parcelable
