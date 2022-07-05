package com.example.PillsKeeper

import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.PillsKeeper.R
import com.example.PillsKeeper.model.Farmaco
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_farmaci_inserire_un_farmaco1.*
import kotlinx.android.synthetic.main.fragment_farmaci_inserire_un_farmaco2.*
import java.time.LocalDate
import java.time.LocalTime
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
    private var dosaggio : Double = 0.0
    private var numDosi : Int = 0
    private lateinit var date : LocalDate
    private lateinit var localImageUri : Uri
    private lateinit var remoteImageUri: String
    private var numPilloleDose = -1
    private var quandoPrendiMedicine = -1
    private val arrayFrequenza = arrayOf("Giorno", "Settimana", "Mese", "Anno")
    private lateinit var adapterFrequenza: ArrayAdapter<String>
    private var spinnerSelection = ""
    private var switch = false
    private var isOk = true

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
        dosaggio = args.dosaggio.toDouble()
        numDosi = args.numDosi
        date = LocalDate.parse(args.textDate, DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALY))
        localImageUri = Uri.parse(args.image)
        adapterFrequenza = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, arrayFrequenza)
        spinnerFrequenza.adapter = adapterFrequenza


        numeroPerDose.addOnButtonCheckedListener{ toggleButtonGroup, checkedId, isChecked ->
            if(isChecked){
                editTextDose2.inputType = InputType.TYPE_NULL
                editTextDose2.setBackgroundResource(R.drawable.text_view_border_light)
                editTextDose2.setHintTextColor(Color.parseColor("#60454773"))
                textView15.setTextColor(Color.parseColor("#B6B5EB"))
                when(checkedId){
                    R.id.button2 -> numPilloleDose = 0
                    R.id.button3 -> numPilloleDose = 1
                    R.id.button4 -> numPilloleDose = 2
                }
            }
            else{
                if (toggleButtonGroup.checkedButtonId == View.NO_ID) {
                    editTextDose2.inputType = InputType.TYPE_CLASS_NUMBER
                    editTextDose2.setBackgroundResource(R.drawable.text_view_border)
                    editTextDose2.setHintTextColor(Color.parseColor("#99454773"))
                    textView15.setTextColor(Color.parseColor("#454773"))
                    numPilloleDose = -1
                }
            }
        }

        frequenzaFarmaco.addOnButtonCheckedListener{ toggleButtonGroup, checkedId, isChecked ->
            if(isChecked){
                editTextTime3.inputType = InputType.TYPE_NULL
                editTextTime3.setBackgroundResource(R.drawable.text_view_border_light)
                editTextTime3.setHintTextColor(Color.parseColor("#60454773"))
                textView54.setTextColor(Color.parseColor("#B6B5EB"))
                when(checkedId){
                    R.id.mattina -> quandoPrendiMedicine = 0
                    R.id.mezzogiorno -> quandoPrendiMedicine = 1
                    R.id.sera -> quandoPrendiMedicine = 2
                }
            }
            else{
                if (toggleButtonGroup.checkedButtonId == View.NO_ID) {
                    editTextTime3.inputType = InputType.TYPE_CLASS_DATETIME
                    editTextTime3.setBackgroundResource(R.drawable.text_view_border)
                    editTextTime3.setHintTextColor(Color.parseColor("#99454773"))
                    textView54.setTextColor(Color.parseColor("#454773"))
                    quandoPrendiMedicine = -1
                }
            }
        }

        switch2.setOnCheckedChangeListener { buttonView, isChecked ->
            switch = isChecked
        }
        spinnerFrequenza.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spinnerSelection = arrayFrequenza[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                spinnerSelection = ""
            }
        }
        textView9.setOnClickListener(this)
        textView57.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.textView57 -> {
                navc.navigate (R.id.actionToFarmaciVisualizza)
            }
            R.id.textView9 -> {
                var quantoNePrendi = 0.0
                lateinit var orario: LocalTime
                var ripetizione = -1
                if(numPilloleDose == -1){
                    if(editTextDose.text.toString() == "") {
                        editTextDose.setBackgroundResource(R.drawable.text_view_border_red)
                        isOk = false
                    }
                    else{
                        try {
                            quantoNePrendi = editTextDose.text.toString().toDouble()
                            editTextDose.setBackgroundResource(R.drawable.text_view_border)
                        }
                        catch (e:Exception){
                            Toast.makeText(requireActivity(), "Inserire la dose correttamente", Toast.LENGTH_LONG).show()
                            editTextDose.setBackgroundResource(R.drawable.text_view_border_red)
                            isOk = false
                        }
                    }
                }
                else{
                    quantoNePrendi =
                        if (numPilloleDose == 0)
                            0.5
                    else
                        numPilloleDose.toDouble()
                    editTextDose.setBackgroundResource(R.drawable.text_view_border)
                }

                if(isOk){
                    if(quandoPrendiMedicine == -1){
                        try {
                            orario = LocalTime.parse(editTextTime3.text.toString(), DateTimeFormatter.ofPattern("hh:mm"))
                            editTextTime3.setBackgroundResource(R.drawable.text_view_border)
                        }
                        catch (e : Exception){
                            editTextTime3.setBackgroundResource(R.drawable.text_view_border_red)
                            Toast.makeText(requireActivity(), "Inserire la dose correttamente", Toast.LENGTH_LONG).show()
                            isOk = false
                        }
                    }
                    else{
                        when(quandoPrendiMedicine){
                            0 -> orario = LocalTime.parse("08:00")
                            1 -> orario = LocalTime.parse("12:00")
                            2 -> orario = LocalTime.parse("20:00")
                        }
                        editTextTime3.setBackgroundResource(R.drawable.text_view_border)
                    }
                }

                if (isOk){
                    try {
                        ripetizione = editTextDose.text.toString().toInt()
                        editTextDose.setBackgroundResource(R.drawable.text_view_border)
                        if (spinnerSelection == ""){
                            spinnerFrequenza.setBackgroundResource(R.drawable.text_view_border_red)
                            Toast.makeText(requireActivity(), "Selezionare la frequenza della ripetizione", Toast.LENGTH_LONG).show()
                            isOk = false
                        }
                        else{
                            spinnerFrequenza.setBackgroundResource(R.drawable.text_view_border)
                        }
                    }
                    catch (e : Exception){
                        editTextDose.setBackgroundResource(R.drawable.text_view_border_red)
                        Toast.makeText(requireActivity(), "Inserire una frequenza valida", Toast.LENGTH_LONG).show()
                        isOk = false
                    }
                }

                if(isOk){
                    //Salvataggio immagine su Cloud Storage
                    val orarioString = orario.toString()
                    val textDate = date.toString()
                    val storageAccess = cloudStorage.child("Farmaci/"+ uid + "/" + localImageUri.lastPathSegment)
                    storageAccess.putFile(localImageUri).addOnSuccessListener {
                        storageAccess.downloadUrl.addOnSuccessListener {
                            remoteImageUri = it.toString()

                            val farmaco = Farmaco(nomeFarmaco, nomeCommerciale, casaProduttrice, dosaggio,numDosi, textDate, quantoNePrendi, switch, orarioString, ripetizione, spinnerSelection, remoteImageUri)
                            db.collection("Utenti").document(uid.toString()).collection("Farmaco").document(nomeFarmaco+dosaggio).set(farmaco)
                                .addOnSuccessListener { Toast.makeText(requireActivity(), "Farmaco salvato con successo!!", Toast.LENGTH_LONG).show()
                                    navc.navigate (R.id.actionToFarmaciVisualizza) }
                                .addOnFailureListener { Toast.makeText(requireActivity(), "Errore salvataggio nel database", Toast.LENGTH_LONG).show() }
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
                }
            }
        }
    }
}