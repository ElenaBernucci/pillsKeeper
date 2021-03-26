package com.example.pagina1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class farmaci : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmaci)
    }

    fun startMenuIniziale(v: View) {
        val intent = Intent(this@farmaci, menu_iniziale::class.java)
        startActivity(intent)
    }
}