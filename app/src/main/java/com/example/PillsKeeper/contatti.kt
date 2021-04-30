package com.example.PillsKeeper

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class contatti : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contatti)
    }
    fun startMenuIniziale(v: View) {
        val intent = Intent(this@contatti, MenuIniziale::class.java)
        startActivity(intent)
    }
}