package com.example.pagina1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class dottore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dottore)
    }

    fun startMenuIniziale(v: View) {
        val intent = Intent(this@dottore, menu_iniziale::class.java)
        startActivity(intent)
    }
}