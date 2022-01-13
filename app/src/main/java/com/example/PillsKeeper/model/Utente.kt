package com.example.PillsKeeper.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize
data class Utente (var nome: String,
              var cognome: String,
              var dataDiNascita: String,
              var email: String,
              var codiceFiscale: String,

              var altezza: Int,
              var peso: Double,
              var circonferenzaVita: Int,
              var massaMagra: Double,
              var massaGrassa: Double,

              var allergie: String?,

              var patologie: String?,

              var gruppoSanguigno: String,

              var imageURL: String): Parcelable {
    constructor () : this("", "","", "", "", 0, 0.0, 0,0.0, 0.0, "", "", "", "")
}