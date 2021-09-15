package com.example.PillsKeeper.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.net.URL
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Parcelize
data class Farmaco (var nomeFarmaco: String,
               var nomeCommerciale: String,
               var casaProduttrice: String,

               var dosaggio: Double,
               var dosiInUnaScatola: Int,
               var scadenza: String,

               var quantoNePrendi: Double,
               var farmacoContinuativo: Boolean,

               var orario: String,
               var ripetizione: Int,
               var ogniQuanto: String,
               var imageURL: String): Parcelable{
    constructor () : this("", "","", 0.0, 0, "", 0.0, false, "", 0, "", "")
}