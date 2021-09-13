package com.example.PillsKeeper.utente

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.PillsKeeper.FarmaciInserireUnFarmaco1Directions
import com.example.PillsKeeper.R
import kotlinx.android.synthetic.main.fragment_farmaci_inserire_un_farmaco1.*
import kotlinx.android.synthetic.main.fragment_impostazioni_modifica1.*
import kotlinx.android.synthetic.main.fragment_log_loggato.*
import kotlinx.android.synthetic.main.fragment_log_loggato.textViewModificaDottore22
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.isInitialized as isInitialized

private const val PICK_IMAGE_CODE = 1234
private const val TAG = "CreateActivity"
lateinit var navc: NavController

class ImpostazioniModifica1 : Fragment(), View.OnClickListener {

    private var photoUri: Uri? = null
    private lateinit var imagePickText : TextView
    private lateinit var image : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_impostazioni_modifica1, container, false)
        image = view.findViewById(R.id.imageView12)
        imagePickText = view.findViewById(R.id.textView16)
        imagePickText.setOnClickListener {
            pickImage()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)
        textView19.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        navc?.navigate(R.id.actionToImpostazioniModifica2)

        val nome = editTextNomeProfilo.text.toString()
        val cognome = editTextCognomeProfilo.text.toString()
        val dataNascita = editTextDataNascitaProfilo.text.toString()
        val emailProfilo = editTextEmailProfilo.text.toString()
        val codiceFiscale = editTextCodicefiscaleProfilo.text.toString()
        //variabili di controllo campi non vuoti

        val nomeOK : Boolean
        val cognomeOK : Boolean
        val dataNascitaOK :Boolean
        val emailProfiloOK : Boolean
        val codiceFiscaleOK : Boolean
        val photoUriOK : Boolean

        //check sui campi vuoti
        if(nome == "") {
            nomeOK = false
            editTextNomeProfilo.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            nomeOK = true
            editTextNomeProfilo.setBackgroundResource(R.drawable.text_view_border)
        }

        if (cognome == "") {
            cognomeOK = false
            editTextCognomeProfilo.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            cognomeOK = true
            editTextCognomeProfilo.setBackgroundResource(R.drawable.text_view_border)
        }

        if(codiceFiscale == "") {
            codiceFiscaleOK = false
            editTextCodicefiscaleProfilo.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            codiceFiscaleOK = true
            editTextCodicefiscaleProfilo.setBackgroundResource(R.drawable.text_view_border)
        }

        if(dataNascita == "") {
            dataNascitaOK = false
            editTextDate.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            try {
                LocalDate.parse(dataNascita, DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALY))
                dataNascitaOK = true
                editTextDate.setBackgroundResource(R.drawable.text_view_border)
            }
            catch (e : Exception){
                dataNascitaOK = false
                editTextDate.setBackgroundResource(R.drawable.text_view_border_red)
            }
        }

        if (!this::photoUri.isInitialized){
            photoUriOK = false
            Toast.makeText(requireActivity(), "Inserire una foto", Toast.LENGTH_SHORT).show()
        }
        else
            photoUriOK = true

        if(nomeOK && cognomeOK && dataNascitaOK && emailProfiloOK && codiceFiscaleOK && photoUriOK) {
            val action = FarmaciInserireUnFarmaco1Directions.actionInserireUnFarmaco2(
                nome,
                cognome,
                dataNascita,
                emailProfilo,
                codiceFiscale,
                photoUri.toString()
            )
            navc?.navigate(action)
        }
        else{
            Toast.makeText(requireActivity(), "Riemipire correttamente tutti i campi", Toast.LENGTH_LONG).show()
        }
    }

    private fun pickImage(){
        val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
        imagePickerIntent.type = "image/*"
        startActivityForResult(imagePickerIntent, com.example.PillsKeeper.PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == com.example.PillsKeeper.PICK_IMAGE_CODE){
            if(resultCode == Activity.RESULT_OK){
                photoUri = data?.data!!
                //var file = File(photoUri?.path)
                //IMAGE CHANGE
                Glide.with(this!!).load(photoUri).circleCrop().into(image)
            }else{
                Toast.makeText(context, "image pick canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    }

}