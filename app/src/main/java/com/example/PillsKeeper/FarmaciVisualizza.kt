package com.example.PillsKeeper

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.PillsKeeper.R
import com.example.PillsKeeper.adapters.farmaciAdapter
import com.example.PillsKeeper.adapters.farmaciBaseAdapter
import com.example.PillsKeeper.model.Farmaco
import com.example.PillsKeeper.model.FarmacoMinimal
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_farmaci_visualizza.*

class FarmaciVisualizza : Fragment() {

    private val db = Firebase.firestore
    private var listaFarmaci : DatabaseReference? = FirebaseDatabase.getInstance().getReference()
    private var listaFarmaciEventListener: ChildEventListener = getListaFarmciEventListener()
    private val list = ArrayList<FarmacoMinimal>()
    private val farmaci: List<FarmacoMinimal> = ArrayList()

    private fun getListaFarmciEventListener(): ChildEventListener {
        val childEventListener = object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("onChildAdded", snapshot.key!!)
                val farmacoNuovo = snapshot.getValue(FarmacoMinimal::class.java)
                list.add(farmacoNuovo!!)
                RecycleViewFarmaci.adapter!!.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        return childEventListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_farmaci_visualizza, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //creaLista()

        listaFarmaci!!.child("farmaciTest").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        RecycleViewFarmaci.adapter = farmaciAdapter(requireContext(), farmaci)
        RecycleViewFarmaci.layoutManager = LinearLayoutManager(requireContext())
        RecycleViewFarmaci.setHasFixedSize(true)
        Log.d("Elenco nel database", listaFarmaci.toString())
    }

    private fun creaLista() {
        val elencoFarmaci = arrayOf("Acarbosio", "Acebutololo", "Paracetamolo", "Acetazolamide", "Acido acetoidrossamico",
            "Acetilcisteina", "Acetilprocainamide", "Acitretina", "Aclidinio", "Aciclovir", "Adalimumab", "Adapalene", "Adefovir", "Adenosina",
            "Aflibercept", "Agalsidasi beta", "Albendazolo", "Albiglutide", "Albuterolo", "Alcaftadina", "Aldesleuchina", "Alectinib", "Alemtuzumab",
            "Alendronato", "Alfuzosina", "Alirocumab", "Aliskiren", "Allopurinolo", "Almotriptan",
            "Alogliptin", "Alosetron", "Alprazolam", "Alprostadil")
        //val list = ArrayList<FarmacoMinimal>()
        var i = 0
        for(farmaco in elencoFarmaci) {
            val drawable = when (i % 3) {
                0 -> R.drawable.logo
                1 -> R.drawable.amu_bubble_mask
                else -> R.drawable.common_google_signin_btn_icon_dark
            }
            val item = FarmacoMinimal(drawable, farmaco, (i%3).toString())
            listaFarmaci!!.child("farmaciTest").child(farmaco).setValue(item)
            //list += item
            i++
        }
        Log.d("Creazione Lista", "Ho fatto tutto")
    }

    private fun ListaFarmaci(): List<FarmacoMinimal>{
        val list = ArrayList<FarmacoMinimal>()


        return list
    }

    override fun onStart() {
        super.onStart()
        listaFarmaci!!.addChildEventListener(listaFarmaciEventListener)
    }

    override fun onStop() {
        super.onStop()
        if(listaFarmaciEventListener != null)
            listaFarmaci!!.removeEventListener(listaFarmaciEventListener)
    }
}