package com.example.PillsKeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    fun startMenuIniziale(v: View) {
        val intent = Intent(this@MainActivity, MenuIniziale::class.java)
        startActivity(intent)
    }

}