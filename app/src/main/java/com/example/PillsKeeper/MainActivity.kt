package com.example.PillsKeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.example.PillsKeeper.adapters.farmaciAdapter
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() , AdapterView.OnItemClickListener{

    private var listView:ListView ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@MainActivity, MenuIniziale::class.java)
            startActivity(intent)
        }, 3000)

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


    }
}