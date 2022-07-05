package com.example.PillsKeeper


import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.PillsKeeper.adapters.farmaciAdapter
import com.example.PillsKeeper.model.Farmaco
import com.example.PillsKeeper.model.FarmacoMinimal
import com.example.PillsKeeper.model.Utente
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.firebase.ui.auth.util.ExtraConstants
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_farmaci_visualizza.*
import kotlinx.android.synthetic.main.fragment_iniziale.*
import java.time.LocalTime


class FirstFragment : Fragment(), View.OnClickListener {

    lateinit var navc:NavController
    val user = Firebase.auth.currentUser
    val uid = user?.uid
    val db = Firebase.firestore
    val storage = Firebase.storage

    // [START auth_fui_create_launcher]
    // See: https://developer.android.com/training/basics/intents/result
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }
    // [END auth_fui_create_launcher]

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_iniziale, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)
        imageButtonReminder2.setOnClickListener(this)
        imageButtonFarmaci2.setOnClickListener(this)
        imageButtonReminder2.setOnClickListener(this)
        imageButtonFarmacia2.setOnClickListener(this)
        imageButtonMedico2.setOnClickListener(this)
        textView68.setOnClickListener(this)
        textView19.setOnClickListener(this)

        if(uid == null){
            navc.navigate(R.id.action_to_inizialeAccessoFragment)
        }
        else{
            db.collection("Utenti").document(uid.toString()).collection("Dati personali")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val utente: Utente = document.toObject()
                        //Log.d("AccessoFarmaci", farmaco.toString())
                        val httpsReference = storage.getReferenceFromUrl(utente.imageURL)
                        val image: Long = 512 * 512
                        httpsReference.getBytes(image).addOnSuccessListener {

                            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                            immagineProfilo.setImageBitmap(bitmap)
                        }.addOnFailureListener{
                            Toast.makeText(requireContext(), "Impossibile caricare l'immagine", Toast.LENGTH_LONG).show()
                        }

                        val nomeCognome = utente.nome + " " + utente.cognome
                        textView66.text = nomeCognome
                    }

                }
                .addOnFailureListener { exception ->
                    Log.d("AccessoFarmaci", "Error getting documents: ", exception)
                }
        }

        /*db.collection("Utenti").document(uid.toString()).collection("farmacoTest").document("farm").set(city)
            .addOnSuccessListener { Log.d("Firestore", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("Firestore", "Error writing document", e) }
        Log.d("getInstance", uid.toString())*/
    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.imageButtonReminder -> {
                createSignInIntent()
            }
            R.id.imageButtonFarmaci2 -> {
                db.collection("Utenti").document(uid.toString()).collection("Farmaco")
                    .get()
                    .addOnSuccessListener { result ->
                        if (result.isEmpty){
                            navc.navigate(R.id.actionToNessunFarmaco)
                        } else{
                            navc.navigate(R.id.fromFirstFragmentToFarmaciVisualizza)
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Error Loading Database", Toast.LENGTH_LONG).show()
                    }
            }

            R.id.imageButtonReminder2 -> {
                navc.navigate(R.id.actionToNoReminder)
            }

            R.id.imageButtonFarmacia2 -> {
                navc.navigate(R.id.action_firstFragment_to_mapsActivity2)
            }

            R.id.imageButtonMedico2 -> {
                db.collection("Utenti").document(uid.toString()).collection("Dottore")
                    .get()
                    .addOnSuccessListener { result ->
                        if (result.isEmpty)
                            navc.navigate(R.id.actionToDottoreNessunDottore)
                        else
                            navc.navigate(R.id.from_firstFragment_to_dottore_visualizza)
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Error Loading Database", Toast.LENGTH_LONG).show()
                    }

            }
            R.id.textView19 -> {
                db.collection("Utenti").document(uid.toString()).collection("Dati personali")
                    .get()
                    .addOnSuccessListener { result ->
                        if (result.isEmpty){
                            navc.navigate(R.id.from_firstFragment_to_impostazioniModifica)
                        } else{
                            navc.navigate(R.id.from_firstFragment_to_logLoggato)
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Error Loading Database", Toast.LENGTH_LONG).show()
                    }
            }
            R.id.textView68 -> {
                signOut()
                navc.navigate(R.id.action_to_inizialeAccessoFragment)
            }
        }
    }

    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            EmailBuilder().build(),
            //AuthUI.IdpConfig.PhoneBuilder().build(),
            IdpConfig.GoogleBuilder().build())
        //AuthUI.IdpConfig.FacebookBuilder().build(),
        //AuthUI.IdpConfig.TwitterBuilder().build())

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.mipmap.loghino) // Set logo drawable
            //.setTheme(R.style.) // Set theme
            .build()
        signInLauncher.launch(signInIntent)
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
            .signOut(requireContext())
            .addOnCompleteListener {
                Toast.makeText(requireContext(), "Logout effettuato con successo", Toast.LENGTH_LONG).show()
            }
        // [END auth_fui_result]
    }
}
