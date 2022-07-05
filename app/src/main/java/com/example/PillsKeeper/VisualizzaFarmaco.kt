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
import kotlinx.android.synthetic.main.fragment_visualizza_farmaco.*

class visualizzaFarmaco : Fragment(), View.OnClickListener {

    private val args: visualizzaFarmacoArgs by navArgs()
    lateinit var navc: NavController
    private val db = Firebase.firestore
    private val uid = Firebase.auth.currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            navc.navigate(R.id.fromVisualizzaFarmacoTofarmaciVisualizza)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_visualizza_farmaco, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)
        textView58.setOnClickListener(this)
        textViewEliminafarmaco.setOnClickListener(this)

        Glide.with(this!!).load(args.farmaco.imageURL).circleCrop().into(imageView6)
        textViewNomeFarmaco.text = args.farmaco.nomeFarmaco
        textViewNomeCommerciale.text = args.farmaco.nomeCommerciale
        textViewCasaProd.text = args.farmaco.casaProduttrice
        textViewDosaggio.text = args.farmaco.dosaggio.toString()
        textViewNumDosiScatola.text = args.farmaco.dosiInUnaScatola.toString()
        textViewScadenza.text = args.farmaco.scadenza
        textViewQuantoPrendi.text = args.farmaco.quantoNePrendi.toString()
        textViewQuandoPrendere.text = args.farmaco.ogniQuanto
        if(args.farmaco.farmacoContinuativo)
            textViewNumDosiScatola3.text = "Sì"
        else
            textViewNumDosiScatola3.text = "No"
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.textView58 ->{
                navc.navigate(R.id.fromVisualizzaFarmacoTofarmaciVisualizza)
            }
            R.id.textViewEliminafarmaco ->{
                db.collection("Utenti").document(uid.toString()).collection("Farmaco").document(args.farmaco.nomeFarmaco + args.farmaco.dosaggio.toString())
                    .delete().addOnSuccessListener {
                        Toast.makeText(requireContext(), "Farmaco eliminato con successo", Toast.LENGTH_LONG)
                        db.collection("Utenti").document(uid.toString()).collection("Farmaco")
                            .get().addOnSuccessListener { result ->
                                if (result.isEmpty){
                                    navc.navigate(R.id.from_visualizzaFarmaco_to_farmaciNessunFarmaco2)
                                } else{
                                    navc.navigate(R.id.fromVisualizzaFarmacoTofarmaciVisualizza)
                                }
                            }

                    }
                    .addOnFailureListener{
                        Toast.makeText(requireContext(), "Errore nell'eliminazione del farmaco", Toast.LENGTH_LONG)
                    }
            }
        }
    }
}