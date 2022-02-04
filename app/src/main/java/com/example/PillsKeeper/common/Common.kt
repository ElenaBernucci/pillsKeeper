package com.example.PillsKeeper.common

import com.example.PillsKeeper.Remote.IGoogleAPIService
import com.example.PillsKeeper.Remote.RetrofitClient

object Common {

    private val GOOGLE_API_URL="https://maps.googleapis.com/"

    val googleApiService: IGoogleAPIService
        get()=RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService::class.java)
}