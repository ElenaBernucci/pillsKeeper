package com.example.PillsKeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ListView
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private var listView:ListView ? = null
    private var itemListAdapters:itemListAdapters ? = null
    private var arrayList: ArrayList<itemList> ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.pills_keeper)


        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@MainActivity, MenuIniziale::class.java)
            startActivity(intent)
        }, 3000)

    }
}