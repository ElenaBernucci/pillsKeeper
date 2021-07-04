package com.example.PillsKeeper.model

import java.util.*

data class Dottore (var nomeD: String,
               var cognomeD: String,
               var specializzazione: String?,

               var mailD: String,
               var telefonoD: Long,
               var utenteId: String) {
}