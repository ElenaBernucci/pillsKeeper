package com.example.PillsKeeper

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_menu_iniziale.*
import kotlinx.android.synthetic.main.fragment_iniziale.*

class MenuIniziale : AppCompatActivity(), View.OnClickListener {

    lateinit var navc: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_iniziale)


    }

    fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navc= Navigation.findNavController(view)
        imageButtonContatti.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        navc?.navigate(R.id.actionToFragmentNessunContatto)
    }

}
