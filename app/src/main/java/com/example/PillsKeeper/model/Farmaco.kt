package com.example.PillsKeeper.model
;

import java.net.URL
import java.util.*

class Farmaco (var nomeFarmaco: String,
               var nomeCommerciale: String,
               var casaProduttrice: String,

               var dosaggio: Int,
               var dosiInUnaScatola: Int,
               var scadenza: Date,

               var quantoNePrendi: String,
               var farmacoContinuativo: Boolean,

               var orario: String,
               var ripetizione: String,
               var imageURL: String,
               var utenteId: String){

}