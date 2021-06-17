package com.example.PillsKeeper.reminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.PillsKeeper.R

class ReminderNessunReminder : Fragment(), View.OnClickListener {

    lateinit var navc: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminder_nessun_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)
        view.findViewById<ImageButton>(R.id.imageButtonReminder4).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        navc?.navigate(R.id.actionInserireReminder)
    }
}