package com.example.PillsKeeper

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu_iniziale.*
import kotlinx.android.synthetic.main.fragment_iniziale.*
import kotlinx.android.synthetic.main.activity_menu_iniziale.btnPlus as btnPlus1

class MenuIniziale : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_iniziale)

        val firstFragment=FirstFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.Fragment,firstFragment)
            commit()
        }

    }

    fun startFunzioni(v: View) {
        val intent = Intent(this@MenuIniziale, funzioni::class.java)
        startActivity(intent)
    }

    fun startReminder(v: View) {
        val intent = Intent(this@MenuIniziale, funzioni::class.java)
        startActivity(intent)
    }

    fun startFarmacia(v: View) {
        val intent = Intent(this@MenuIniziale, funzioni::class.java)
        startActivity(intent)
    }

    fun startDottore(v: View) {
        val intent = Intent(this@MenuIniziale, funzioni::class.java)
        startActivity(intent)
    }

    fun startContatti(v: View) {
        val intent = Intent(this@MenuIniziale, funzioni::class.java)
        startActivity(intent)
    }

}
