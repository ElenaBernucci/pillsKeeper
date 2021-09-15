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
import com.bumptech.glide.Glide
import com.example.PillsKeeper.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_farmaci_inserire_un_farmaco1.*
import kotlinx.android.synthetic.main.fragment_impostazioni_modifica.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class ImpostazioniModifica : Fragment(), View.OnClickListener {

    private val db = Firebase.firestore
    private val uid = Firebase.auth.currentUser?.uid
    private val cloudStorage = FirebaseStorage.getInstance().getReference()
    private lateinit var photoUri: Uri
    private lateinit var imagePickText : TextView
    private lateinit var image : ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_impostazioni_modifica, container, false)
        image = view.findViewById(R.id.imageView9)
        imagePickText = view.findViewById(R.id.textView8)
        imagePickText.setOnClickListener {
            pickImage()
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(p0: View?) {

        val nomeProfilo = editTextNomeProfilo2.text.toString()
        val cognomeProfilo = editTextCognomeProfilo2.text.toString()
        val dataNascitaProfilo = editTextDataNascitaProfilo2.text.toString()
        val emailProfilo = editTextEmailProfilo2.text.toString()
        val codiceFiscaleProfilo = editTextCodicefiscaleProfilo2.text.toString()
        val altezzaProfilo = editTextAltezza.text.toString()
        val pesoProfilo = editTextPeso.text.toString()
        val circonferenzaVitaProfilo = editTextCirconferenzavita.text.toString()
        val massaMagraProfilo = editTextMassamagra.text.toString()
        val massaGrassaProfilo = editTextMassagrassa.text.toString()
        val allergieProfilo = editTextAllergiegenerali.text.toString()
        val patologieProfilo = editTextPatologie3.text.toString()
        //controllo campi non vuoti
        val nomeProfiloOK : Boolean
        val cognomeProfiloOK : Boolean
        var dataNascitaProfiloOK : Boolean
        val codiceFiscaleProfiloOK : Boolean
        val altezzaProfiloOK : Boolean
        val pesoProfiloOK : Boolean
        val emailProfiloOK : Boolean
        val circonferenzaVitaProfiloOK : Boolean
        val massaMagraProfiloOK : Boolean
        val massaGrassaProfiloOK : Boolean
        val allergieProfiloOK : Boolean
        val patologieProfiloOK : Boolean
        val photoUriOK : Boolean
        val isOK : Boolean

        //check sui campi vuoti
        if(nomeProfilo == "") {
            nomeProfiloOK = false
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            nomeProfiloOK = true
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border)
        }
        if(cognomeProfilo == "") {
            cognomeProfiloOK = false
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            cognomeProfiloOK = true
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border)
        }
        if(dataNascitaProfilo == "") {
            dataNascitaProfiloOK = false
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            try {
                LocalDate.parse(dataNascitaProfilo, DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALY))
                dataNascitaProfiloOK = true
                editTextDate.setBackgroundResource(R.drawable.text_view_border)
            }
            catch (e : Exception){
                dataNascitaProfiloOK = false
                editTextDate.setBackgroundResource(R.drawable.text_view_border_red)
            }
        }
        if(emailProfilo == "") {
            emailProfiloOK = false
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            emailProfiloOK = true
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border)
        }
        if(codiceFiscaleProfilo == "") {
            codiceFiscaleProfiloOK = false
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            codiceFiscaleProfiloOK = true
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border)
        }
        if(altezzaProfilo == "") {
            altezzaProfiloOK = false
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            altezzaProfiloOK = true
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border)
        }
        if(pesoProfilo == "") {
            pesoProfiloOK = false
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            pesoProfiloOK = true
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border)
        }
        if(circonferenzaVitaProfilo == "") {
            circonferenzaVitaProfiloOK = false
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            circonferenzaVitaProfiloOK = true
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border)
        }
        if(massaMagraProfilo== "") {
            massaMagraProfiloOK = false
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            massaMagraProfiloOK = true
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border)
        }
        if(massaGrassaProfilo == "") {
            massaGrassaProfiloOK = false
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            massaGrassaProfiloOK = true
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border)
        }
        if(allergieProfilo == "") {
            allergieProfiloOK = false
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            allergieProfiloOK = true
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border)
        }
        if(patologieProfilo == "") {
            patologieProfiloOK = false
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            patologieProfiloOK = true
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border)
        }
        if (!this::photoUri.isInitialized){
            photoUriOK = false
            Toast.makeText(requireActivity(), "Inserire una foto", Toast.LENGTH_SHORT).show()
        }
        else
            photoUriOK = true

        if(nomeProfiloOK && cognomeProfiloOK && dataNascitaProfiloOK && emailProfiloOK && codiceFiscaleProfiloOK && altezzaProfiloOK && photoUriOK && pesoProfiloOK && circonferenzaVitaProfiloOK && massaMagraProfiloOK && massaGrassaProfiloOK && allergieProfiloOK && patologieProfiloOK) {
           /* val action = FarmaciInserireUnFarmaco1Directions.actionInserireUnFarmaco2(
                photoUri.toString()
            )
            navc?.navigate(action)*/
        }
        else{
            Toast.makeText(requireActivity(), "Riemipire correttamente tutti i campi", Toast.LENGTH_LONG).show()
            isOK = false
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
                //CAMBIO DI IMMAGINE
                Glide.with(this!!).load(photoUri).circleCrop().into(image)
            }else{
                Toast.makeText(context, "image pick canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

}