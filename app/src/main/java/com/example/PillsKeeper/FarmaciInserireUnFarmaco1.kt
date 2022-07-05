package com.example.PillsKeeper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_farmaci_inserire_un_farmaco1.*
import org.w3c.dom.Text
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private const val TAG = "CreateActivity"
const val PICK_IMAGE_CODE = 1234

class FarmaciInserireUnFarmaco1 : Fragment(), View.OnClickListener {

    lateinit var navc: NavController
    private lateinit var photoUri: Uri
    private lateinit var imagePickText : TextView
    private lateinit var image : ImageView
    private val db = Firebase.firestore
    private val uid = Firebase.auth.currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            db.collection("Utenti").document(uid.toString()).collection("Farmaco")
                .get()
                .addOnSuccessListener{ result ->
                    if (result.isEmpty)
                        navc.navigate(R.id.from_faraciInserireUnFarmaco1_to_farmaciNessunFarmaco)
                    else
                        navc.navigate(R.id.action_faraciInserireUnFarmaco1_to_farmaciVisualizza)
                }
                .addOnFailureListener { navc.navigate(R.id.from_faraciInserireUnFarmaco1_to_firstFragment) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_farmaci_inserire_un_farmaco1, container, false)
        image = view.findViewById(R.id.imageView3)
        imagePickText = view.findViewById(R.id.textView5)
        imagePickText.setOnClickListener {
            pickImage()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)
        textView11.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        val nomeFarmaco = editTextNomeFarmaco.text.toString()
        val nomeCommerciale = editTextNomeCommerciale.text.toString()
        val casaProduttrice = editTextCasaProduttrice.text.toString()
        val dosaggioText = editTextDosaggio.text.toString()
        val numDosiText = editTextNumDosi.text.toString()
        val textDate = editTextDate.text.toString()
        //variabili di controllo campi non vuoti
        val nomeFarmacoOK : Boolean
        val nomeCommercialeOK : Boolean
        val casaProduttriceOK :Boolean
        var dosaggioOK : Boolean
        var numDosiOK : Boolean
        var textDateOK : Boolean
        val photoUriOK : Boolean

        var dosaggio = 0.0F
        var numDosi = 0

        //check sui campi vuoti
        if(nomeFarmaco == "") {
            nomeFarmacoOK = false
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            nomeFarmacoOK = true
            editTextNomeFarmaco.setBackgroundResource(R.drawable.text_view_border)
        }

        if (nomeCommerciale == "") {
            nomeCommercialeOK = false
            editTextNomeCommerciale.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            nomeCommercialeOK = true
            editTextNomeCommerciale.setBackgroundResource(R.drawable.text_view_border)
        }

        if(casaProduttrice == "") {
            casaProduttriceOK = false
            editTextCasaProduttrice.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            casaProduttriceOK = true
            editTextCasaProduttrice.setBackgroundResource(R.drawable.text_view_border)
        }

        try{
            dosaggio = dosaggioText.toFloat()
            dosaggioOK = true
            editTextDosaggio.setBackgroundResource(R.drawable.text_view_border)
        }
        catch (e:Exception){
            dosaggioOK = false
            editTextDosaggio.setBackgroundResource(R.drawable.text_view_border_red)
        }

        try {
            numDosi = numDosiText.toInt()
            numDosiOK = true
            editTextNumDosi.setBackgroundResource(R.drawable.text_view_border)
        }
        catch (e: Exception){
            numDosiOK = false
            editTextNumDosi.setBackgroundResource(R.drawable.text_view_border_red)
        }


        if(textDate == "") {
            textDateOK = false
            editTextDate.setBackgroundResource(R.drawable.text_view_border_red)
        }
        else {
            try {
                LocalDate.parse(textDate, DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALY))
                textDateOK = true
                editTextDate.setBackgroundResource(R.drawable.text_view_border)
            }
            catch (e : Exception){
                textDateOK = false
                editTextDate.setBackgroundResource(R.drawable.text_view_border_red)
            }


        }

        if (!this::photoUri.isInitialized){
            photoUriOK = false
            Toast.makeText(requireActivity(), "Inserire una foto", Toast.LENGTH_SHORT).show()
        }
        else
            photoUriOK = true

        if(nomeFarmacoOK && nomeCommercialeOK && casaProduttriceOK && dosaggioOK && numDosiOK && textDateOK && photoUriOK) {

            val action = FarmaciInserireUnFarmaco1Directions.actionInserireUnFarmaco2(
                nomeFarmaco,
                nomeCommerciale,
                casaProduttrice,
                dosaggio,
                numDosi,
                textDate,
                photoUri.toString()
            )
            navc.navigate(action)
        }
        else{
            Toast.makeText(requireActivity(), "Riemipire correttamente tutti i campi", Toast.LENGTH_LONG).show()
        }
    }

    private fun pickImage(){
        val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
        imagePickerIntent.type = "image/*"
        startActivityForResult(imagePickerIntent, PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_CODE){
            if(resultCode == Activity.RESULT_OK){
                photoUri = data?.data!!
                //var file = File(photoUri?.path)
                //IMAGE CHANGE
                Glide.with(this!!).load(photoUri).circleCrop().into(image)
            }else{
                Toast.makeText(context, "image pick canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}