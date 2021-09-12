package com.example.PillsKeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import kotlinx.android.synthetic.main.activity_smsprovvisoria.*

class SMSProvvisoria : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smsprovvisoria)

        btnSend.setOnClickListener{

            var obj = SmsManager.getDefault()
            obj.sendTextMessage("338393729", null, "Medicina", null, null)

        }

    }
}