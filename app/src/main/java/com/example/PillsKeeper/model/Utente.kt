package com.example.PillsKeeper.model

import java.util.*

class Utente (var nome: String,
              var cognome: String,
              var dataDiNascita: Date,
              var email: String,
              var codiceFiscale: String,

              var altezza: Int,
              var circonferenzaVita: String?,
              var massaMagra: String?,
              var massaGrassa: String?,

              var allergieGenerali: String?,
              var allergieFarmaci: String?,

              var patologie: String?,

              var gruppoSabguigno: String,

              var imageURL: String,
              ) {
}