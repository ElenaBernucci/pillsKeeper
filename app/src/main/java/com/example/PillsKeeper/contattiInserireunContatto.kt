package com.example.PillsKeeper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_contatti_inserireun_contatto.*
import kotlinx.android.synthetic.main.fragment_iniziale.*
import kotlinx.android.synthetic.main.fragment_iniziale.imageButtonReminder

class contattiInserireunContatto : Fragment(), View.OnClickListener {

    lateinit var navc: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contatti_inserireun_contatto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)
        textView49.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        navc?.navigate(R.id.actionToContattiContattoSelezionato)
    }
}