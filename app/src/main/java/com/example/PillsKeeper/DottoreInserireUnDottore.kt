package com.example.PillsKeeper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.PillsKeeper.R
import com.example.PillsKeeper.model.Dottore
import com.example.PillsKeeper.model.Farmaco
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_dottore_inserire_un_dottore.*

class dottoreInserireUnDottore : Fragment(), OnClickListener {

    lateinit var navc: NavController
    private lateinit var photoUri: Uri
    private lateinit var imagePickText : TextView
    private lateinit var image : ImageView
    private val db = Firebase.firestore
    private val uid = Firebase.auth.currentUser?.uid
    private val cloudStorage = FirebaseStorage.getInstance().getReference()
    private lateinit var remoteImageUri: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            db.collection("Utenti").document(uid.toString()).collection("Dottore")
                .get()
                .addOnSuccessListener { result ->
                    if (result.isEmpty)
                        navc.navigate(R.id.from_dottoreInserireUnDottore_to_dottoreNessunDottore)
                    else
                        navc.navigate(R.id.action_to_dottore_visualizza)
                }
                .addOnFailureListener{
                    navc.navigate(R.id.from_dottoreInserireUnDottore_to_firstFragment)
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dottore_inserire_un_dottore, container, false)
        image = view.findViewById(R.id.imageView4)
        imagePickText = view.findViewById(R.id.textViewInserisciFoto)
        imagePickText.setOnClickListener {
            pickImage()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navc= Navigation.findNavController(view)
        textView23.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.textView34 -> {
                db.collection("Utenti").document(uid.toString()).collection("Dottore")
                    .get()
                    .addOnSuccessListener { result ->
                        if (result.isEmpty)
                            navc.navigate(R.id.from_dottoreInserireUnDottore_to_dottoreNessunDottore)
                        else
                            navc.navigate(R.id.action_to_dottore_visualizza)
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Error Loading Database", Toast.LENGTH_LONG).show()
                    }
            }
            R.id.textView23 ->{
                val nomeDottore = editTextNome.text.toString()
                val cognomeDottore = editTextCognome.text.toString()
                val specializzDottore = editTextSpecializzazione.text.toString()
                val emailDottore = editTextMail.text.toString()
                val telefonoDottore = editTextTelefono.text.toString()

                //varibili di controllo campi non vuoti
                val nomeDottoreOK: Boolean
                val cognomeDottoreOK: Boolean
                val emailDottoreOK: Boolean
                val telefonoDottoreOK: Boolean
                val photoUriOK: Boolean

                if (nomeDottore == ""){
                    nomeDottoreOK = false
                    editTextNome.setBackgroundResource(R.drawable.text_view_border_red)
                }
                else{
                    nomeDottoreOK = true
                    editTextNome.setBackgroundResource(R.drawable.text_view_border)
                }

                if (cognomeDottore == ""){
                    cognomeDottoreOK = false
                    editTextCognome.setBackgroundResource(R.drawable.text_view_border_red)
                }
                else{
                    cognomeDottoreOK = true
                    editTextCognome.setBackgroundResource(R.drawable.text_view_border)
                }

                if (emailDottore == ""){
                    emailDottoreOK = false
                    editTextMail.setBackgroundResource(R.drawable.text_view_border_red)
                }
                else{
                    emailDottoreOK = true
                    editTextMail.setBackgroundResource(R.drawable.text_view_border)
                }

                if (telefonoDottore == ""){
                    telefonoDottoreOK = false
                    editTextTelefono.setBackgroundResource(R.drawable.text_view_border_red)
                }
                else{
                    telefonoDottoreOK = true
                    editTextTelefono.setBackgroundResource(R.drawable.text_view_border)
                }

                if (!this::photoUri.isInitialized){
                    photoUriOK = false
                    Toast.makeText(requireActivity(), "Inserire una foto", Toast.LENGTH_SHORT).show()
                }
                else
                    photoUriOK = true

                if(nomeDottoreOK && cognomeDottoreOK && emailDottoreOK && telefonoDottoreOK && photoUriOK){
                    val storageAccess = cloudStorage.child("Dottori/"+ uid + "/" + photoUri.lastPathSegment)
                    storageAccess.putFile(photoUri).addOnSuccessListener {
                        storageAccess.downloadUrl.addOnSuccessListener {
                            remoteImageUri = it.toString()

                            val dottore = Dottore(nomeDottore, cognomeDottore, specializzDottore, emailDottore, telefonoDottore.toLong(), remoteImageUri)
                            db.collection("Utenti").document(uid.toString()).collection("Dottore").document(nomeDottore+cognomeDottore).set(dottore)
                                .addOnSuccessListener { Toast.makeText(requireActivity(), "Dottore salvato con successo!!", Toast.LENGTH_LONG).show()
                                    navc.navigate (R.id.action_to_dottore_visualizza) }
                                .addOnFailureListener { Toast.makeText(requireActivity(), "Errore salvataggio nel database", Toast.LENGTH_LONG).show() }
                        }
                            .addOnFailureListener {
                                Toast.makeText(requireContext(), "Error getting access to the Database, try again", Toast.LENGTH_LONG).show()
                                Log.d("DottoreImageUpload", it.message!!)
                            }
                    }.addOnFailureListener {
                        Toast.makeText(
                            requireContext(),
                            "Error getting access to the Database, try again",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("DottoreImageUpload", it.message!!)
                    }
                }
            }
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