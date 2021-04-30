package com.example.PillsKeeper


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation


class FirstFragment : Fragment(), View.OnClickListener {

    lateinit var navc:NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_iniziale, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)
        view.findViewById<ImageButton>(R.id.imageButtonReminder).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        navc?.navigate(R.id.iniziale_to_iniziale_accesso)
    }

}
