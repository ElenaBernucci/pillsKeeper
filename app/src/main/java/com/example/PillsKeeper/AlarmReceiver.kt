package com.example.PillsKeeper

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver: BroadcastReceiver (){
    override fun onReceive(context: Context?, intent: Intent?) {

        //aprire la destination activity (notification)
        val i = Intent (context,notification::class.java)
        intent!!.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0,i, 0)

        //creiamo oggetto notifica con i vari attributi riga 26 porta ad aprire l'activity destinazione
        val builder = NotificationCompat.Builder (context!!, "PillsKeeper")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("PillsKeeper AlarmManager")
            .setContentText("reminder notifica")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        //mostra la notifica
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123, builder.build())

    }
}