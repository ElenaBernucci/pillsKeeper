package com.example.PillsKeeper.model

import java.net.URL
import java.time.LocalDate
import java.util.*

data class Farmaco (var nomeFarmaco: String,
               var nomeCommerciale: String,
               var casaProduttrice: String,

               var dosaggio: Int,
               var dosiInUnaScatola: Int,
               var scadenza: LocalDate,

               var quantoNePrendi: String,
               var farmacoContinuativo: Boolean,

               var orario: String,
               var ripetizione: String,
               var imageURL: Int){

}