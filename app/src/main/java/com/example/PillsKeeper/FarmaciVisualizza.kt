package com.example.PillsKeeper

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.PillsKeeper.adapters.farmaciAdapter
import com.example.PillsKeeper.model.Farmaco
import com.example.PillsKeeper.model.FarmacoMinimal
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_farmaci_visualizza.*

class FarmaciVisualizza : Fragment(), farmaciAdapter.OnItemClickListener, View.OnClickListener {

    lateinit var navc: NavController
    private val db = Firebase.firestore
    val uid = Firebase.auth.currentUser?.uid
    private var farmaci: ArrayList<Farmaco> = ArrayList()



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
        navc= Navigation.findNavController(view)
        bottoneAggiungi.setOnClickListener(this)

        db.collection("Utenti").document(uid.toString()).collection("Farmaco")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    //Log.d("AccessoFarmaci", "${document.id} => ${document.data}")
                    val farmaco : Farmaco = document.toObject()
                    Log.d("AccessoFarmaci", farmaco.toString())
                    farmaci.add(farmaco)
                }
                RecycleViewFarmaci.adapter = farmaciAdapter(requireContext(), farmaci, this)
                RecycleViewFarmaci.layoutManager = LinearLayoutManager(requireContext())
                RecycleViewFarmaci.setHasFixedSize(true)

            }
            .addOnFailureListener { exception ->
                Log.d("AccessoFarmaci", "Error getting documents: ", exception)
            }


    }

    override fun onItemClick(position: Int) {
        Log.d("OnClick Recycler View", "Item $position selected")
        val clickedItem:Farmaco = farmaci[position]
        Log.d("Item Clicked", farmaci[position].casaProduttrice)
        val action = FarmaciVisualizzaDirections.actionTovisualizzaFarmaco(clickedItem)
        navc.navigate(action)
    }

    override fun onClick(v: View?) {
        navc.navigate(R.id.fromVisualizzaToInserireFarmaco1)
    }

    /*private fun creaLista() {
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
            val item = FarmacoMinimal(drawable, farmaco, (i%3+1).toString())
            db.collection("Utenti").document(uid.toString()).collection("farmacoTest").document(farmaco).set(item)
                .addOnSuccessListener { Log.d("Firestore", "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w("Firestore", "Error writing document", e) }
            //Log.d("getInstance", uid.toString())

            //list += item
            i++
        }
        Log.d("Creazione Lista", "Ho fatto tutto")
    }*/
}