package com.example.pillsKeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class reminder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
    }

    fun startMenuIniziale(v: View) {
        val intent = Intent(this@reminder, menu_iniziale::class.java)
        startActivity(intent)
    }
}