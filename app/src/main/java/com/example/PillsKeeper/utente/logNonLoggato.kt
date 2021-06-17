package com.example.PillsKeeper.utente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.PillsKeeper.R
import kotlinx.android.synthetic.main.fragment_log_non_loggato.*

class logNonLoggato : Fragment(), View.OnClickListener {

    lateinit var navc: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_non_loggato, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)
        textViewModificaDottore3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        navc?.navigate(R.id.actionToLogLoggato)
    }
}