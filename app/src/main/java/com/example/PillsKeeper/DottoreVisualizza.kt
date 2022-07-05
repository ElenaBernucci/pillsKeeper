package com.example.PillsKeeper

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.PillsKeeper.adapters.DottoriAdapter
import com.example.PillsKeeper.model.Dottore
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_dottore_visualizza.*

class dottore_visualizza : Fragment(), DottoriAdapter.OnItemClickListener, View.OnClickListener {

    lateinit var navc: NavController
    private val db = Firebase.firestore
    val uid = Firebase.auth.currentUser?.uid
    private var dottori: ArrayList<Dottore> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            navc.navigate(R.id.from_dottore_visualizza_to_firstFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dottore_visualizza, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)
        button7.setOnClickListener(this)

        db.collection("Utenti").document(uid.toString()).collection("Dottore")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("AccessoDottori", "${document.id} => ${document.data}")
                    val dottore : Dottore = document.toObject()
                    Log.d("AccessoDottori", dottore.toString())
                    dottori.add(dottore)
                }
                RecycleViewDottori.adapter = DottoriAdapter(requireContext(), dottori, this)
                RecycleViewDottori.layoutManager = LinearLayoutManager(requireContext())
                RecycleViewDottori.setHasFixedSize(true)

            }
            .addOnFailureListener { exception ->
                Log.d("AccessoDottori", "Error getting documents: ", exception)
            }
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.button7 -> navc.navigate(R.id.from_dottore_visualizza_to_dottoreInserireUnDottore)
        }
    }

    override fun onItemClick(position: Int) {
        Log.d("OnClick Recycler View", "Item $position selected")
        val clickedItem: Dottore = dottori[position]
        Log.d("Item Clicked", dottori[position].nomeD)
        val action = dottore_visualizzaDirections.actionToDottoreSelezionato(clickedItem)
        navc.navigate(action)
    }

    override fun onBackPressed(v: View?) {
        navc.navigate(R.id.from_dottore_visualizza_to_firstFragment)
    }
}