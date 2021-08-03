package com.example.PillsKeeper.model

import android.net.Uri
import java.net.URL
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class Farmaco (var nomeFarmaco: String,
               var nomeCommerciale: String,
               var casaProduttrice: String,

               var dosaggio: Int,
               var dosiInUnaScatola: Int,
               var scadenza: String,

               var quantoNePrendi: Double,
               var farmacoContinuativo: Boolean,

               var orario: String,
               var ripetizione: Int,
               var ogniQuanto: String,
               var imageURL: String){

}