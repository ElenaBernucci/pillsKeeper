package com.example.PillsKeeper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_contatti_inserireun_contatto.*
import kotlinx.android.synthetic.main.fragment_iniziale.*
import kotlinx.android.synthetic.main.fragment_iniziale.imageButtonReminder
import java.io.File

private const val PICK_IMAGE_CODE = 1234

class contattiInserireunContatto : Fragment(), View.OnClickListener {

    lateinit var navc: NavController
    private var photoUri: Uri? = null
    private lateinit var imagePickText : TextView
    private lateinit var image : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_contatti_inserireun_contatto, container, false)
        image = view.findViewById(R.id.imageView9)
        imagePickText = view.findViewById(R.id.textView42)
        imagePickText.setOnClickListener {
            pickImage()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navc= Navigation.findNavController(view)
        textView49.setOnClickListener(this)
    }

    private fun pickImage(){
        val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
        imagePickerIntent.type = "image/*"
        startActivityForResult(imagePickerIntent, PICK_IMAGE_CODE)
    }

    override fun onClick(v: View?) {
        navc?.navigate(R.id.actionToContattiContattoSelezionato)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_CODE){
            if(resultCode == Activity.RESULT_OK){
                photoUri = data?.data!!
                var file = File(photoUri?.path)
                //IMAGE CHANGE
                Glide.with(this!!).load(photoUri).circleCrop().into(image)
            }else{
                Toast.makeText(context, "image pick canceled", Toast.LENGTH_SHORT).show()
            }
        }

    }

}