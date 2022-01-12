package com.example.PillsKeeper.model

import java.util.*

data class Utente (var nome: String,
              var cognome: String,
              var dataDiNascita: String,
              var email: String,
              var codiceFiscale: String,

              var altezza: Int,
              var circonferenzaVita: Int,
              var massaMagra: Double,
              var massaGrassa: Double,

              var allergie: String?,

              var patologie: String?,

              var gruppoSanguigno: String,

              var imageURL: String,
              )