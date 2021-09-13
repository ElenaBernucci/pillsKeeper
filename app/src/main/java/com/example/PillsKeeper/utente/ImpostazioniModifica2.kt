package com.example.PillsKeeper.utente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.PillsKeeper.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDate

private val db = Firebase.firestore
private val uid = Firebase.auth.currentUser?.uid
private val cloudStorage = FirebaseStorage.getInstance().getReference()
private lateinit var nomeProfilo: String
private lateinit var cognomeProfilo : String
private lateinit var dataNascita : LocalDate
private lateinit var emailProfilo: String
private lateinit var codiceFiscale : String

class ImpostazioniModifica2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_impostazioni_modifica2, container, false)
    }
}