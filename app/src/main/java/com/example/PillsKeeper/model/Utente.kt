package com.example.PillsKeeper.model

import java.util.*

data class Utente (var nome: String,
              var cognome: String,
              var dataDiNascita: Date,
              var email: String,
              var codiceFiscale: String,

              var altezza: Int,
              var circonferenzaVita: String?,
              var massaMagra: String?,
              var massaGrassa: String?,

              var allergie: String?,

              var patologie: String?,

              var gruppoSanguigno: String,

              var imageURL: String,
              ) {
}