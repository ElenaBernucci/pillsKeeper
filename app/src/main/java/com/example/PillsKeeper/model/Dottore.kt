package com.example.PillsKeeper.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Dottore (var nomeD: String,
               var cognomeD: String,
               var specializzazione: String?,

               var mailD: String,
               var telefonoD: Long,
               var imageURL: String): Parcelable {
    constructor() : this("", "", "", "", 0, "")
}