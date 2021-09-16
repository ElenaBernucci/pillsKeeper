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

  //  override fun onBackPressed(v: View?) {
    //    navc.navigate(R.id.fromVisualizzaFarmacoatoFirstFragment)
    //}

}