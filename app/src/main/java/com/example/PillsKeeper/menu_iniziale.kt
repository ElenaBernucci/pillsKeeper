package com.example.PillsKeeper

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class menu_iniziale : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_iniziale)
    }

    fun startFarmaci(v: View) {
        val intent = Intent(this@menu_iniziale, pillole::class.java)
        startActivity(intent)
    }

    fun startReminder(v: View) {
        val intent = Intent(this@menu_iniziale, reminder::class.java)
        startActivity(intent)
    }

    fun startFarmacia(v: View) {
        val intent = Intent(this@menu_iniziale, farmacia::class.java)
        startActivity(intent)
    }

    fun startDottore(v: View) {
        val intent = Intent(this@menu_iniziale, dottore::class.java)
        startActivity(intent)
    }

    fun startContatti(v: View) {
        val intent = Intent(this@menu_iniziale, contatti::class.java)
        startActivity(intent)
    }

}