package com.example.PillsKeeper.utente

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.PillsKeeper.R
import com.example.PillsKeeper.model.Utente
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_iniziale.*
import kotlinx.android.synthetic.main.fragment_log_loggato.*

class logLoggato : Fragment(), View.OnClickListener {

    lateinit var navc: NavController
    val user = Firebase.auth.currentUser
    val uid = user?.uid
    val db = Firebase.firestore
    val storage = Firebase.storage

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_log_loggato, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)
        textViewUtenteModifica.setOnClickListener(this)
        textViewUtenteIndietro.setOnClickListener(this)

        db.collection("Utenti").document(uid.toString()).collection("Dati personali")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val utente: Utente = document.toObject()
                    val httpsReference = storage.getReferenceFromUrl(utente.imageURL)
                    val image: Long = 512 * 512
                    httpsReference.getBytes(image).addOnSuccessListener {

                        val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                        imageView11.setImageBitmap(bitmap)
                    }.addOnFailureListener{
                        Toast.makeText(requireContext(), "Impossibile caricare l'immagine", Toast.LENGTH_LONG).show()
                    }

                    val nome = textViewUtenteNome.text.toString() + ": " + utente.nome
                    textViewUtenteNome.text = nome
                    val cognome = textViewUtenteCognome.text.toString() + ": " + utente.cognome
                    textViewUtenteCognome.text = cognome
                    val dataDiNascita = textViewUtenteDataDiNascita.text.toString() + ": " + utente.dataDiNascita
                    textViewUtenteDataDiNascita.text = dataDiNascita
                    val email = textViewUtenteEmail.text.toString() + ": " + utente.email
                    textViewUtenteEmail.text = email
                    val codiceFiscale = textViewUtenteCodiceFiscale.text.toString() + ": " + utente.codiceFiscale
                    textViewUtenteCodiceFiscale.text = codiceFiscale
                    val altezza = textViewUtenteAltezza.text.toString() + ": " + utente.altezza.toString() + " cm"
                    textViewUtenteAltezza.text = altezza
                    val peso = textViewUtentePeso.text.toString() + ": " + utente.peso.toString() + " kg"
                    textViewUtentePeso.text = peso
                    val circoferenzaVita = textViewUtenteCirconferenzaVita.text.toString() + ": " + utente.circonferenzaVita.toString() + " cm"
                    textViewUtenteCirconferenzaVita.text = circoferenzaVita
                    val massaMagra = textViewUtenteMassaMagra.text.toString() + ": " + utente.massaMagra.toString() + " kg"
                    textViewUtenteMassaMagra.text = massaMagra
                    val massaGrassa = textViewUtenteMassaGrassa.text.toString() + ": " + utente.massaGrassa.toString() + " kg"
                    textViewUtenteMassaGrassa.text = massaGrassa
                    var allergie = textViewUtenteAllergie.text.toString() + ": "
                    if(utente.allergie == "")
                        allergie += "No"
                    else
                        allergie += utente.allergie
                    textViewUtenteAllergie.text = allergie
                    var patologie = textViewUtentePatologie.text.toString() + ": "
                    if(utente.patologie == "")
                        patologie += "No"
                    else
                        patologie += utente.patologie
                    textViewUtentePatologie.text = patologie
                    val gruppoSanguigno = textViewUtenteGruppoSanguigno.text.toString() + ": " + utente.gruppoSanguigno
                    textViewUtenteGruppoSanguigno.text = gruppoSanguigno
                }

            }
            .addOnFailureListener { exception ->
                Log.d("AccessoFarmaci", "Error getting documents: ", exception)
            }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.textViewUtenteIndietro -> {
                navc.navigate(R.id.action_logLoggato_to_firstFragment)
            }
            R.id.textViewUtenteModifica -> {
                navc.navigate(R.id.action_logLoggato_to_impostazioniModifica)
            }
        }

    }
}