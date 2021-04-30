package com.example.PillsKeeper

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class funzioni : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_funzioni)

            val firstFragment=FirstFragment()

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.Fragment,firstFragment)
                commit()
            }

        }
    fun startFunzioni(v: View) {
        val intent = Intent(this@funzioni, MenuIniziale::class.java)
        startActivity(intent)
    }

    }
