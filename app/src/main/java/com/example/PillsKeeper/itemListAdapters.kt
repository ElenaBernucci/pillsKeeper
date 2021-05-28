package com.example.PillsKeeper

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class itemListAdapters (var context: Context, var arrayList: ArrayList<itemList>) : BaseAdapter() {


    override fun getItem(position: Int): Any {
        return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.farmaci_row, null)

        var imageView: ImageView = view.findViewById(R.id.immagineFarmaco)
        var titleText: TextView = view.findViewById(R.id.NomeFarmaco)
        var detailText: TextView = view.findViewById(R.id.dose)

        var itemList:itemList = arrayList.get(position)
        imageView.setImageResource(itemList.icons!!)
        titleText.text = itemList.title
        detailText.text = itemList.details

        return view!!
    }
}