package com.example.PillsKeeper.model

data class FarmacoMinimal(var image: Int,
                        var nomeFarmaco: String,
                        var dosaggio: String){
    constructor () : this(0, "","")
}