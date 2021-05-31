package com.example.PillsKeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() , AdapterView.OnItemClickListener{

    private var listView:ListView ? = null
    private var itemListAdapters:itemListAdapters ? = null
    private var arrayList: ArrayList<itemList> ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.ListView)
        arrayList = ArrayList ()
        arrayList = setDataArrayList()
        itemListAdapters = itemListAdapters(applicationContext, arrayList!!)
        listView?.adapter = itemListAdapters



        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@MainActivity, MenuIniziale::class.java)
            startActivity(intent)
        }, 3000)

    }

    private fun setDataArrayList() : ArrayList<itemList>{

        var arrayList:ArrayList <itemList> = ArrayList()

            arrayList.add(itemList(R.drawable.loghino_background, "tachipirina", "io non lo so"))

            arrayList.add(itemList(R.drawable.loghino_background, "moment", "io non lo so2"))


            return arrayList!!

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        var items:itemList = arrayList?.get(position)!!
        Toast.makeText(applicationContext , items.title, Toast.LENGTH_LONG).show()

    }
}