package com.example.PillsKeeper.utente

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.PillsKeeper.R
import com.example.PillsKeeper.model.Utente
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
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
    private var primaRegistrazione = false
    private lateinit var localImageUri: Uri
    private lateinit var remoteImageUri: String
    private lateinit var imagePickText: TextView
    private lateinit var image: ImageView
    lateinit var navc: NavController
    lateinit var dati: Utente


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navc = Navigation.findNavController(view)
        textView25.setOnClickListener(this)
        textView62.setOnClickListener(this)
        /*db.collection("Utenti").document(uid.toString()).collection("Dati personali")
            .get()
            .addOnSuccessListener { result ->
                primaRegistrazione = result.isEmpty
                if(!primaRegistrazione){
                    for (document in result) {
                        val utente: Utente = document.toObject()

                        editTextNomeProfilo2.hint = utente.nome
                        editTextCognomeProfilo2.hint = utente.cognome
                        editTextDataNascitaProfilo2.hint = utente.dataDiNascita
                        editTextEmailProfilo2.hint = utente.email
                        editTextCodicefiscaleProfilo2.hint = utente.codiceFiscale
                        editTextAltezza.hint = utente.altezza.toString()
                        editTextPeso.hint = utente.peso.toString()
                        editTextCirconferenzavita.hint = utente.circonferenzaVita.toString()
                        editTextMassamagra.hint = utente.massaGrassa.toString()
                        editTextMassagrassa.hint = utente.massaGrassa.toString()
                        if(utente.allergie != "")
                            editTextAllergiegenerali.hint = utente.allergie
                        if(utente.patologie != "")
                            editTextPatologie3.hint = utente.patologie
                        spinnergrupposanguigno.setSelection(getIndex(spinnergrupposanguigno, utente.gruppoSanguigno))
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error Loading Database", Toast.LENGTH_LONG).show()
            }*/
    }

    private fun getIndex(spinnergrupposanguigno: Spinner?, gruppoSanguigno: String): Int {
        val i = 0
        while (i < spinnergrupposanguigno?.getCount()!!) {
            if (spinnergrupposanguigno.getItemAtPosition(i).toString().equals(gruppoSanguigno)) {
                return i
            }
        }
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {

        val nomeProfilo = editTextNomeProfilo2.text.toString()
        val cognomeProfilo = editTextCognomeProfilo2.text.toString()
        val dataNascitaProfilo = editTextDataNascitaProfilo2.text.toString()
        val emailProfilo = editTextEmailProfilo2.text.toString()
        val codiceFiscaleProfilo = editTextCodicefiscaleProfilo2.text.toString()
        val altezzaProfiloText = editTextAltezza.text.toString()
        var altezzaProfilo = 0
        val pesoProfiloText = editTextPeso.text.toString()
        var pesoProfilo = 0.0
        val circonferenzaVitaProfiloText = editTextCirconferenzavita.text.toString()
        var circonferenzaVitaProfilo = 0
        val massaMagraProfiloText = editTextMassamagra.text.toString()
        var massaMagraProfilo = 0.0
        val massaGrassaProfiloText = editTextMassagrassa.text.toString()
        var massaGrassaProfilo = 0.0
        val allergieProfilo = editTextAllergiegenerali.text.toString()
        val patologieProfilo = editTextPatologie3.text.toString()
        val spinnerGruppoSanguigno = spinnergrupposanguigno.selectedItem.toString()
        //controllo campi non vuoti
        var nomeProfiloOK: Boolean
        var cognomeProfiloOK: Boolean
        var dataNascitaProfiloOK: Boolean
        var codiceFiscaleProfiloOK: Boolean
        var altezzaProfiloOK: Boolean
        var pesoProfiloOK: Boolean
        var emailProfiloOK: Boolean
        var circonferenzaVitaProfiloOK: Boolean
        var massaMagraProfiloOK: Boolean
        var massaGrassaProfiloOK: Boolean
        var allergieProfiloOK: Boolean
        var patologieProfiloOK: Boolean
        var spinnerGruppoSanguignoOk: Boolean
        var photoUriOK: Boolean
        val isOK: Boolean



        when (v?.id) {
            R.id.textView62 -> {
                navc.navigate(R.id.from_impostazioniModifica_to_firstFragment)
            }

            R.id.textView25 -> {
                Log.d("Salva", "Hai premuto salva")
                Log.d("primaRegistrazione", primaRegistrazione.toString())
                if (primaRegistrazione) {
                    //check sui campi vuoti
                    if (nomeProfilo == "") {
                        nomeProfiloOK = false
                        editTextNomeProfilo2.setBackgroundResource(R.drawable.text_view_border_red)
                    } else {
                        nomeProfiloOK = true
                        editTextNomeProfilo2.setBackgroundResource(R.drawable.text_view_border)
                    }
                    if (cognomeProfilo == "") {
                        cognomeProfiloOK = false
                        editTextCognomeProfilo2.setBackgroundResource(R.drawable.text_view_border_red)
                    } else {
                        cognomeProfiloOK = true
                        editTextCognomeProfilo2.setBackgroundResource(R.drawable.text_view_border)
                    }
                    if (dataNascitaProfilo == "") {
                        dataNascitaProfiloOK = false
                        editTextDataNascitaProfilo2.setBackgroundResource(R.drawable.text_view_border_red)
                    } else {
                        try {
                            LocalDate.parse(
                                dataNascitaProfilo,
                                DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALY)
                            )
                            dataNascitaProfiloOK = true
                            editTextDataNascitaProfilo2.setBackgroundResource(R.drawable.text_view_border)
                        } catch (e: Exception) {
                            dataNascitaProfiloOK = false
                            editTextDataNascitaProfilo2.setBackgroundResource(R.drawable.text_view_border_red)
                        }
                    }
                    if (emailProfilo == "") {
                        emailProfiloOK = false
                        editTextEmailProfilo2.setBackgroundResource(R.drawable.text_view_border_red)
                    } else {
                        emailProfiloOK = true
                        editTextEmailProfilo2.setBackgroundResource(R.drawable.text_view_border)
                    }
                    if (codiceFiscaleProfilo == "") {
                        codiceFiscaleProfiloOK = false
                        editTextCodicefiscaleProfilo2.setBackgroundResource(R.drawable.text_view_border_red)
                    } else {
                        codiceFiscaleProfiloOK = true
                        editTextCodicefiscaleProfilo2.setBackgroundResource(R.drawable.text_view_border)
                    }
                    if (altezzaProfiloText == "") {
                        altezzaProfiloOK = false
                        editTextAltezza.setBackgroundResource(R.drawable.text_view_border_red)
                    } else {
                        try {
                            altezzaProfilo = altezzaProfiloText.toInt()
                            altezzaProfiloOK = true
                            editTextAltezza.setBackgroundResource(R.drawable.text_view_border)
                        } catch (e: Exception) {
                            altezzaProfiloOK = false
                            editTextAltezza.setBackgroundResource(R.drawable.text_view_border_red)
                        }
                    }
                    if (pesoProfiloText == "") {
                        pesoProfiloOK = false
                        editTextPeso.setBackgroundResource(R.drawable.text_view_border_red)
                    } else {
                        try {
                            pesoProfilo = pesoProfiloText.toDouble()
                            pesoProfiloOK = true
                            editTextPeso.setBackgroundResource(R.drawable.text_view_border)
                        } catch (e: Exception) {
                            pesoProfiloOK = false
                            editTextPeso.setBackgroundResource(R.drawable.text_view_border_red)
                        }

                    }
                    if (circonferenzaVitaProfiloText == "") {
                        circonferenzaVitaProfiloOK = false
                        editTextCirconferenzavita.setBackgroundResource(R.drawable.text_view_border_red)
                    } else {
                        try {
                            circonferenzaVitaProfilo = circonferenzaVitaProfiloText.toInt()
                            circonferenzaVitaProfiloOK = true
                            editTextCirconferenzavita.setBackgroundResource(R.drawable.text_view_border)
                        } catch (e: Exception) {
                            circonferenzaVitaProfiloOK = false
                            editTextCirconferenzavita.setBackgroundResource(R.drawable.text_view_border_red)
                        }
                    }
                    if (massaMagraProfiloText == "") {
                        massaMagraProfiloOK = false
                        editTextMassamagra.setBackgroundResource(R.drawable.text_view_border_red)
                    } else {
                        try {
                            massaMagraProfilo = massaMagraProfiloText.toDouble()
                            massaMagraProfiloOK = true
                            editTextMassamagra.setBackgroundResource(R.drawable.text_view_border)
                        } catch (e: Exception) {
                            massaMagraProfiloOK = false
                            editTextMassamagra.setBackgroundResource(R.drawable.text_view_border_red)
                        }
                    }
                    if (massaGrassaProfiloText == "") {
                        massaGrassaProfiloOK = false
                        editTextMassagrassa.setBackgroundResource(R.drawable.text_view_border_red)
                    } else {
                        try {
                            massaGrassaProfilo = massaGrassaProfiloText.toDouble()
                            massaGrassaProfiloOK = true
                            editTextMassagrassa.setBackgroundResource(R.drawable.text_view_border)
                        } catch (e: Exception) {
                            massaGrassaProfiloOK = false
                            editTextMassagrassa.setBackgroundResource(R.drawable.text_view_border_red)
                        }
                    }

                    if (spinnergrupposanguigno.selectedItem == "- Seleziona -") {
                        spinnerGruppoSanguignoOk = false
                        spinnergrupposanguigno.setBackgroundResource(R.drawable.text_view_border_red)
                    } else {
                        spinnerGruppoSanguignoOk = true
                        spinnergrupposanguigno.setBackgroundResource(R.drawable.text_view_border)
                    }

                    if (!this::localImageUri.isInitialized) {
                        photoUriOK = false
                        Toast.makeText(requireActivity(), "Inserire una foto", Toast.LENGTH_SHORT)
                            .show()
                    } else
                        photoUriOK = true

                    if (nomeProfiloOK && cognomeProfiloOK && dataNascitaProfiloOK && emailProfiloOK && codiceFiscaleProfiloOK && altezzaProfiloOK && photoUriOK && pesoProfiloOK && circonferenzaVitaProfiloOK && massaMagraProfiloOK && massaGrassaProfiloOK && spinnerGruppoSanguignoOk) {
                        /* val action = FarmaciInserireUnFarmaco1Directions.actionInserireUnFarmaco2(
                             photoUri.toString()
                         )*/
                        Log.d("Controlli", "Tutto OK")
                        val storageAccess =
                            cloudStorage.child("Dati personali/" + uid + "/" + localImageUri.lastPathSegment)
                        storageAccess.putFile(localImageUri).addOnSuccessListener {
                            storageAccess.downloadUrl.addOnSuccessListener {
                                remoteImageUri = it.toString()

                                val utente = Utente(nomeProfilo, cognomeProfilo, dataNascitaProfilo, emailProfilo, codiceFiscaleProfilo, altezzaProfilo, pesoProfilo, circonferenzaVitaProfilo, massaMagraProfilo, massaGrassaProfilo, allergieProfilo, patologieProfilo, spinnerGruppoSanguigno, remoteImageUri)
                                db.collection("Utenti").document(uid.toString()).collection("Dati personali").document(
                                    "$nomeProfilo $cognomeProfilo"
                                ).set(utente)
                                    .addOnSuccessListener { Toast.makeText(requireActivity(), "Farmaco salvato con successo!!", Toast.LENGTH_LONG).show()
                                        navc.navigate (R.id.from_impostazioniModifica_to_firstFragment) }
                                    .addOnFailureListener { Toast.makeText(requireActivity(), "Errore salvataggio nel database", Toast.LENGTH_LONG).show() }
                                }
                            }.addOnFailureListener {
                                Toast.makeText(requireContext(), "Error getting access to the Database, try again", Toast.LENGTH_LONG).show()
                            }
                            navc.navigate(R.id.from_impostazioniModifica_to_firstFragment)
                    }
                    else{
                        Toast.makeText(requireActivity(), "Riemipire correttamente tutti i campi", Toast.LENGTH_LONG).show()
                    }
                }
                else {

                        nomeProfiloOK = nomeProfilo != ""
                        cognomeProfiloOK = cognomeProfilo != ""

                        if (dataNascitaProfilo == "") {
                            dataNascitaProfiloOK = false
                            editTextDataNascitaProfilo2.setBackgroundResource(R.drawable.text_view_border_red)
                        } else {
                            try {
                                LocalDate.parse(
                                    dataNascitaProfilo,
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALY)
                                )
                                dataNascitaProfiloOK = true
                                editTextDataNascitaProfilo2.setBackgroundResource(R.drawable.text_view_border)
                            } catch (e: Exception) {
                                dataNascitaProfiloOK = false
                                editTextDataNascitaProfilo2.setBackgroundResource(R.drawable.text_view_border_red)
                            }
                        }
                        emailProfiloOK = emailProfilo != ""
                        codiceFiscaleProfiloOK = codiceFiscaleProfilo != ""
                        altezzaProfiloOK = altezzaProfiloText != ""
                        pesoProfiloOK = pesoProfiloText != ""
                        circonferenzaVitaProfiloOK = circonferenzaVitaProfiloText != ""
                        massaMagraProfiloOK = massaMagraProfiloText != ""
                        massaGrassaProfiloOK = massaGrassaProfiloText != ""
                        allergieProfiloOK = allergieProfilo != ""
                        patologieProfiloOK = patologieProfilo != ""
                        spinnerGruppoSanguignoOk =
                            spinnergrupposanguigno.selectedItem != "- Seleziona -"
                        photoUriOK = this::localImageUri.isInitialized

                        if (!(nomeProfiloOK && cognomeProfiloOK && dataNascitaProfiloOK && emailProfiloOK && codiceFiscaleProfiloOK && altezzaProfiloOK && photoUriOK && pesoProfiloOK && circonferenzaVitaProfiloOK && massaMagraProfiloOK && massaGrassaProfiloOK && allergieProfiloOK && patologieProfiloOK && spinnerGruppoSanguignoOk)) {
                            Toast.makeText(
                                requireContext(),
                                "Riempire almeno uno dei campi",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {

                        }
                    }
                }
            }
        }

    private fun pickImage() {
        val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
        imagePickerIntent.type = "image/*"
        startActivityForResult(imagePickerIntent, com.example.PillsKeeper.PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == com.example.PillsKeeper.PICK_IMAGE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                localImageUri = data?.data!!
                //var file = File(photoUri?.path)
                //CAMBIO DI IMMAGINE
                Glide.with(this!!).load(localImageUri).circleCrop().into(image)
            } else {
                Toast.makeText(context, "image pick canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}