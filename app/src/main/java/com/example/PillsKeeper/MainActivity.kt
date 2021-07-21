package com.example.PillsKeeper

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.example.PillsKeeper.adapters.farmaciAdapter
import com.example.PillsKeeper.model.Farmaco
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() , AdapterView.OnItemClickListener{

    private lateinit var firestoreDb : FirebaseFirestore
    private lateinit var firebaseStorage : StorageReference
    private var listView:ListView ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firestoreDb = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance().reference

        @Suppress("DEPRECATION")
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@MainActivity, MenuIniziale::class.java)
            startActivity(intent)
            finish()
        }, 2500)

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


    }

    /*private fun uploadFarmaco(uri:Uri?){
        // CONTROLLI CAMPI

        if(uri != null) {
            val photoReference = firebaseStorage.child(
                "Farmaci/${System.currentTimeMillis()}-photo" + getFileExtension(uri))
            photoReference.putFile(uri)
                .continueWithTask { photoUploadTask ->
                    photoReference.downloadUrl
                }.continueWithTask { downloadUrlTask ->
                    val farmaco = Farmaco(
                        FarmaciInserireUnFarmaco1.getView()
                    )
                }
        }
    }*/

    private fun getFileExtension(uri: Uri) : String?{
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.contentResolver?.getType(uri))
    }

}