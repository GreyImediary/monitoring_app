package ru.therapyapp.data_mkb.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mkb(
    val name: String,
    val code: String
) : Parcelable
