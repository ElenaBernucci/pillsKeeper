package com.example.PillsKeeper

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.PillsKeeper.databinding.ActivityInviaMailBinding

class inviaMail : AppCompatActivity() {

    lateinit var binding: ActivityInviaMailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInviaMailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtInviaMail.setOnClickListener{

            val email = binding.txtDestinatario.text.toString()
            val oggetto = binding.txtOggetto.text.toString()
            val messaggio = binding.txtCorpo.text.toString()

            val addresses = email.split(",".toRegex()).toTypedArray()

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


        }

    }
}