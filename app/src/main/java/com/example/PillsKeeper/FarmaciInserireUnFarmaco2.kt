package com.example.PillsKeeper

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.PillsKeeper.R
import com.example.PillsKeeper.model.Farmaco
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_farmaci_inserire_un_farmaco1.*
import kotlinx.android.synthetic.main.fragment_farmaci_inserire_un_farmaco2.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class FarmaciInserireUnFarmaco2 : Fragment(), View.OnClickListener {

    lateinit var navc: NavController
    private val args: FarmaciInserireUnFarmaco2Args by navArgs()
    private val db = Firebase.firestore
    private val uid = Firebase.auth.currentUser?.uid
    private val cloudStorage = FirebaseStorage.getInstance().getReference()
    private lateinit var nomeFarmaco: String
    private lateinit var nomeCommerciale : String
    private lateinit var casaProduttrice : String
    private var dosaggio : Int = 0
    private var numDosi : Int = 0
    private lateinit var textDate : LocalDate
    private lateinit var localImageUri : Uri
    private lateinit var remoteImageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_farmaci_inserire_un_farmaco2, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)

        nomeFarmaco = args.nomeFarmaco
        nomeCommerciale = args.nomeCommerciale
        casaProduttrice = args.casaProduttrice
        dosaggio = args.dosaggio
        numDosi = args.numDosi
        textDate = LocalDate.parse(args.textDate, DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALY))
        localImageUri = Uri.parse(args.image)

        textView9.setOnClickListener(this)
        textView57.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.textView57 -> {
                navc.navigate (R.id.actionToFarmaciVisualizza)
            }
            R.id.textView9 -> {
                //Salvataggio immagine su Cloud Storage
                val storageAccess = cloudStorage.child("Farmaci/$uid/")
                storageAccess.putFile(localImageUri).addOnSuccessListener {
                    storageAccess.downloadUrl.addOnSuccessListener {
                        remoteImageUri = it
                    }
                        .addOnFailureListener {
                            Toast.makeText(
                                requireContext(),
                                "Error getting access to the Database, try again",
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("FarmaciImageUpload", it.message!!)
                        }
                }.addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        "Error getting access to the Database, try again",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("FarmaciImageUpload", it.message!!)
                }

                //val farmaco = Farmaco(nomeFarmaco, nomeCommerciale, casaProduttrice, dosaggio,numDosi, textDate,)


                navc.navigate (R.id.actionToFarmaciVisualizza)
            }
        }

    }
}