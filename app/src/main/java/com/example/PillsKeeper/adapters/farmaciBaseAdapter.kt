package com.example.PillsKeeper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.PillsKeeper.R

class farmaciBaseAdapter(private val context: Context, val data: Array<String>): BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var newView = convertView
        if(convertView == null){
            newView = LayoutInflater.from(context).inflate(R.layout.farmaci_row, parent, false)
        }
        else{
            val nomeFarmaco = newView?.findViewById<TextView>(R.id.NomeFarmaco)
            val dose = newView?.findViewById<TextView>(R.id.dose)
            val parts = data[position].split(" ")
            nomeFarmaco!!.text = parts[0]
            dose!!.text = parts[1]
        }
        return newView
    }
}