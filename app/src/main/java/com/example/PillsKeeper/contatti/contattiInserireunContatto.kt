package com.example.PillsKeeper.contatti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.PillsKeeper.FarmaciInserireUnFarmaco1Directions
import com.example.PillsKeeper.R
import kotlinx.android.synthetic.main.activity_invia_mail.*
import kotlinx.android.synthetic.main.fragment_contatti_inserireun_contatto.*

class contattiInserireunContatto : Fragment(), View.OnClickListener {

    lateinit var navc: NavController



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_contatti_inserireun_contatto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navc= Navigation.findNavController(view)
        textView49.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        navc?.navigate(R.id.actionToContattiContattoSelezionato)

        //per il controllo
        val nomeC = editTextNomeC.text.toString()
        val cognomeC = editTextCognomeC.text.toString()
        val parentelaC = editTextParentelaC.text.toString()
        val mailC = editTextEmailC.text.toString()
        val NumeroC = editTextNumeoTC.text.toString()

        //variabili di controllo campi non vuoti
        val nomeCOK : Boolean
        val cognomeOK : Boolean
        val parentelaOK : Boolean
        val mailCOK : Boolean
        val numeroOK : Boolean


        //check sui campi vuoti
        if(nomeC == "") {
            nomeCOK = false
            editTextNomeC.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            nomeCOK = true
            editTextNomeC.setBackgroundResource(R.drawable.text_view_border)
        }
        if(cognomeC == "") {
            cognomeOK = false
            editTextCognomeC.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            cognomeOK = true
            editTextCognomeC.setBackgroundResource(R.drawable.text_view_border)
        }
        if(parentelaC == "") {
            parentelaOK = false
            editTextParentelaC.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            parentelaOK = true
            editTextParentelaC.setBackgroundResource(R.drawable.text_view_border)
        }
        if(mailC == "") {
            mailCOK = false
            editTextEmailC.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            mailCOK = true
            editTextEmailC.setBackgroundResource(R.drawable.text_view_border)
        }
        if(NumeroC == "") {
            numeroOK = false
            editTextNumeoTC.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            numeroOK = true
            editTextNumeoTC.setBackgroundResource(R.drawable.text_view_border)
        }

        /*if(nomeCOK && cognomeOK && parentelaOK && mailCOK && numeroOK) {

            )
            navc?.navigate(action)
        }
        else{
            Toast.makeText(requireActivity(), "Riemipire correttamente tutti i campi", Toast.LENGTH_LONG).show()
        }*/

        //fine dei controlli

    }
}