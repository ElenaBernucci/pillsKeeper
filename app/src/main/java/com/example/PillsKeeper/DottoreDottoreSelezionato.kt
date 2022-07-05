package com.example.PillsKeeper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.PillsKeeper.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_dottore_dottore_selezionato.*
import kotlinx.android.synthetic.main.fragment_visualizza_farmaco.*

class dottoreDottoreSelezionato : Fragment(), View.OnClickListener {

    private val args: dottoreDottoreSelezionatoArgs by navArgs()
    lateinit var navc: NavController
    private val db = Firebase.firestore
    private val uid = Firebase.auth.currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            navc.navigate(R.id.from_dottoreDottoreSelezionato_to_dottore_visualizza)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dottore_dottore_selezionato, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)
        textViewRichiediUnaRicetta.setOnClickListener(this)
        textViewEliminaDottore.setOnClickListener(this)

        Glide.with(this!!).load(args.dottore.imageURL).circleCrop().into(imageView5)
        textView32.text = "Nome: " + args.dottore.nomeD
        textView33.text = "Cognome: " + args.dottore.cognomeD
        textView37.text = "Specializzazione: " + args.dottore.specializzazione
        textView38.text = "Email: " + args.dottore.mailD
        textView39.text = "Telefono: " + args.dottore.telefonoD.toString()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.textViewEliminaDottore -> {
                db.collection("Utenti").document(uid.toString()).collection("Dottore").document(args.dottore.nomeD + args.dottore.cognomeD)
                    .delete().addOnSuccessListener {
                        Toast.makeText(requireContext(), "Dottore eliminato con successo", Toast.LENGTH_LONG)
                        db.collection("Utenti").document(uid.toString()).collection("Dottore")
                            .get().addOnSuccessListener { result ->
                                if (result.isEmpty){
                                    navc.navigate(R.id.from_dottoreDottoreSelezionato_to_firstFragment)
                                } else{
                                    navc.navigate(R.id.from_dottoreDottoreSelezionato_to_dottore_visualizza)
                                }
                            }
                    }
                    .addOnFailureListener{
                        Toast.makeText(requireContext(), "Errore nella cancellazione del dottore", Toast.LENGTH_LONG)
                    }
            }
        }
    }
}