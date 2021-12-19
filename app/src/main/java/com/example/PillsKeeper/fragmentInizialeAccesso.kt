package com.example.PillsKeeper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_iniziale_accesso.*

class InizialeAccessoFragment : Fragment(), View.OnClickListener {

    lateinit var navc: NavController
    var user = Firebase.auth.currentUser
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_iniziale_accesso, container, false)
    }

    // [START auth_fui_create_launcher]
    // See: https://developer.android.com/training/basics/intents/result
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }
    // [END auth_fui_create_launcher]

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc= Navigation.findNavController(view)
        imageButtonReminder.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.imageButtonReminder){
            createSignInIntent()
        }
    }

    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            //AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())
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
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            // Successfully signed in
            user = FirebaseAuth.getInstance().currentUser

            var uid = user?.uid

            db.collection("Utenti").document(uid.toString()).collection("Dati personali")
                .get()
                .addOnSuccessListener { result ->
                    if (result.isEmpty){
                        navc.navigate(R.id.from_inizialeAccesso_to_impostazioniModifica)
                    } else{
                        navc.navigate(R.id.action_to_firstFragment)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Error Loading Database", Toast.LENGTH_LONG).show()
                }

            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            Toast.makeText(requireContext(), "Errore login", Toast.LENGTH_LONG).show()

        }
    }
    // [END auth_fui_result]

}
