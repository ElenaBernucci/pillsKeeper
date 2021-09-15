package com.example.PillsKeeper

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.service.autofill.CharSequenceTransformation
import android.view.LayoutInflater
import android.widget.Toast
//import com.example.PillsKeeper.databinding.ActivityInserireReminderBinding
//import com.example.PillsKeeper.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class inserire_reminder : AppCompatActivity() {

    //private lateinit var binding : ActivityInserireReminderBinding
    private lateinit var picker : MaterialTimePicker
    private lateinit var calendar : Calendar
    private lateinit var alarmManager :AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        /*binding = ActivityInserireReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()

        binding.btncambiaorario.setOnClickListener {
            showTimePicker()
        }

        binding.textView22.setOnClickListener{
            setAlarm()
        }

    }

    private fun setAlarm() {

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent (this, AlarmReceiver ::class.java)

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.setRepeating(

            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent

        )

        alarmManager.setRepeating(

            AlarmManager.RTC_WAKEUP, calendar .timeInMillis,
            AlarmManager.INTERVAL_DAY,pendingIntent
        )

        Toast.makeText (this, "Alarm set Successfuly", Toast .LENGTH_SHORT).show()

    }

    private fun showTimePicker() {

        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("seleziona orario")
            .build()

        picker.show(supportFragmentManager,"PillsKeeper")

        picker.addOnPositiveButtonClickListener {


            if(picker.hour > 12 ){

                binding.orarioreminder.setText(
                    String.format("%02d", picker.hour - 12) + " : " + String.format(
                        "%02d",
                        picker.minute
                    ) + "PM")

            } else{
                String.format("%02d", picker.hour ) + " : " + String.format(
                    "%02d",
                    picker.minute
                ) + "AM"

            }
            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY ] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
        }


    }

    //canale per le notifiche
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name: CharSequence = "PillsKeeper Reminder"
            val description = "channel for alarm manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("PillsKeeper", name,importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(channel)
        }*/

    }
}