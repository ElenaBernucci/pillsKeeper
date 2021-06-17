package com.example.PillsKeeper.farmaci

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.PillsKeeper.R
import kotlinx.android.synthetic.main.fragment_farmaci_inserire_un_farmaco3.*


class farmaciInserireUnFarmaco3 : Fragment(), View.OnClickListener {

    lateinit var navc: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_farmaci_inserire_un_farmaco3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)
        textView18.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        navc?.navigate(R.id.actionToFarmaciConDeiFarmaci)
    }
}