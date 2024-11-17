package com.example.hobbytracker.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.hobbytracker.R

class HobbyAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = intent?.getIntExtra("notificationId", 0) ?: 0
        val hobbyTitle = intent?.getStringExtra("hobbyTitle") ?: ""

        val chanelId = "hobby_reminder_chanel"
        val chanel = NotificationChannel(chanelId, "Hobby Reminder", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(chanel)

        val notification = NotificationCompat.Builder(context, chanelId)
            .setContentTitle("Hobby Time!!!")
            .setContentText("It's time to $hobbyTitle")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .build()
        notificationManager.notify(notificationId,notification)
    }
}