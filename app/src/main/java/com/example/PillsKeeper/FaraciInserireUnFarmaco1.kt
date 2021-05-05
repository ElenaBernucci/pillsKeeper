package com.example.PillsKeeper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation

class FaraciInserireUnFarmaco1 : Fragment(), View.OnClickListener {

    lateinit var navc: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faraci_inserire_un_farmaco1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)
        view.findViewById<TextView>(R.id.textView11).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        navc?.navigate(R.id.actionInserireUnFarmaco2)
    }
}