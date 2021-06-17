package com.example.PillsKeeper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_menu_iniziale.*
import kotlinx.android.synthetic.main.fragment_iniziale.*
import java.io.File

private const val PICK_IMAGE_CODE = 1234

open class MenuIniziale : AppCompatActivity(), View.OnClickListener {

    lateinit var navc: NavController
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_iniziale)


    }

    fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navc= Navigation.findNavController(view)
        imageButtonContatti.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        navc?.navigate(R.id.actionToFragmentNessunContatto)
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
                var file = File(photoUri?.path)
            }else{
                Toast.makeText(this, "image pick canceled", Toast.LENGTH_SHORT).show()
            }
        }

    }

}
