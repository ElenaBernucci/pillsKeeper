package com.example.PillsKeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class contatti : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contatti)
    }
    fun startMenuIniziale(v: View) {
        val intent = Intent(this@contatti, menu_iniziale::class.java)
        startActivity(intent)
    }
}