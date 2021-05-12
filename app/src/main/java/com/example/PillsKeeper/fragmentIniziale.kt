package com.example.PillsKeeper


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_iniziale.*


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
        imageButtonReminder.setOnClickListener(this)
        imageButtonFarmaci2.setOnClickListener(this)
        imageButtonReminder2.setOnClickListener(this)
        imageButtonFarmacia2.setOnClickListener(this)
        imageButtonMedico2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.imageButtonReminder -> {
                navc?.navigate(R.id.iniziale_to_iniziale_accesso)
            }
            R.id.imageButtonFarmaci2 -> {
                navc?.navigate(R.id.actionToNessunFarmaco)
            }

            R.id.imageButtonReminder2 -> {
                navc?.navigate(R.id.actionToNoReminder)
            }

            R.id.imageButtonFarmacia2 -> {
                navc?.navigate(R.id.actionToFarmaciaNessunaFarmacia)
            }

            R.id.imageButtonMedico2 -> {
                navc?.navigate(R.id.actionToDottoreNessunDottore)
            }

        }


    }

}
