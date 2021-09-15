package com.example.PillsKeeper

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
//import com.example.PillsKeeper.databinding.ActivityInviaMailBinding
import kotlinx.android.synthetic.main.activity_invia_mail.*
import kotlinx.android.synthetic.main.fragment_farmaci_inserire_un_farmaco1.*

class inviaMail : AppCompatActivity() {

    //lateinit var binding: ActivityInviaMailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*binding = ActivityInviaMailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtInviaMail.setOnClickListener{

            val email = binding.txtDestinatario.text.toString()
            val oggetto = binding.txtOggetto.text.toString()
            val messaggio = binding.txtCorpo.text.toString()
            val addresses = email.split(",".toRegex()).toTypedArray()

            //per il controllo
            val Destinatario = txtDestinatario.text.toString()
            val Oggetto = txtOggetto.text.toString()
            val Corpo = txtCorpo.text.toString()

            //variabili di controllo campi non vuoti
            val DestinatarioOK : Boolean
            val OggettoOK : Boolean
            val CorpoOK : Boolean



            //check sui campi vuoti
            if(Destinatario == "") {
                DestinatarioOK = false
                txtDestinatario.setBackgroundResource(R.drawable.text_view_border_red)
            }
            else {
                DestinatarioOK = true
                txtDestinatario.setBackgroundResource(R.drawable.text_view_border)
            }
            if(Oggetto == "") {
                OggettoOK = false
                txtOggetto.setBackgroundResource(R.drawable.text_view_border_red)
            }
            else {
                OggettoOK = true
                txtOggetto.setBackgroundResource(R.drawable.text_view_border)
            }
            if(Corpo == "") {
                CorpoOK = false
                txtCorpo.setBackgroundResource(R.drawable.text_view_border_red)
            }
            else {
                CorpoOK = true
                txtCorpo.setBackgroundResource(R.drawable.text_view_border)
            }

            /*if(OggettoOK && CorpoOK && DestinatarioOK) {

            )
            navc?.navigate(action)
        }
        else{
            Toast.makeText(requireActivity(), "Riemipire correttamente tutti i campi", Toast.LENGTH_LONG).show()
        }*/

            //fine dei controlli


            val intent = Intent(Intent.ACTION_SEND).apply {

                data = Uri.parse("Mail to:")
                putExtra(Intent.EXTRA_EMAIL, addresses)
                putExtra(Intent.EXTRA_SUBJECT, oggetto)
                putExtra(Intent.EXTRA_TEXT, messaggio)

            }

            if(intent.resolveActivity(packageManager) != null){

                startActivity(intent)

            }else{
                Toast.makeText(this@inviaMail, "Required app is not installed", Toast.LENGTH_SHORT).show()
            }


        }*/

    }
}