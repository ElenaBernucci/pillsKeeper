package com.example.PillsKeeper.dottore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.PillsKeeper.R
import kotlinx.android.synthetic.main.fragment_dottore_inserire_un_dottore.*

class dottoreInserireUnDottore : Fragment(), OnClickListener {

    lateinit var navc: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dottore_inserire_un_dottore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navc= Navigation.findNavController(view)
        textView23.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        navc?.navigate(R.id.action_to_dottore_visualizza)
    }


}