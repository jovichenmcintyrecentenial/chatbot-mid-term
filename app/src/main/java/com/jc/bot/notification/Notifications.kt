package com.jc.bot.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.jc.bot.ChatActivity
import com.jc.bot.R
import com.jc.bot.models.MyConstants
import java.text.SimpleDateFormat
import java.util.*

class Notifications(private val context: Context) {
    private val CHANNEL_ID = "my_channel"
    private var NOTIFICATION_ID = 1

    init {
    }

    fun showNotification(title: String, message: String, data:Bundle) {


        val avatar = data.getInt(MyConstants.CHAT_AVATAR)

        NOTIFICATION_ID++

        val intent = Intent(context, ChatActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        val dateFormat = SimpleDateFormat("MMM d, yyyy @ hh:mm a", Locale.getDefault())


        val remoteViews = RemoteViews(context.packageName, R.layout.my_custom_layout)

        remoteViews.setTextViewText(R.id.textView1, title)
        remoteViews.setTextViewText(R.id.textView2, message)
        remoteViews.setImageViewResource(R.id.avatar, avatar)
        remoteViews.setTextViewText(R.id.date_text, dateFormat.format(System.currentTimeMillis()))
//      remoteViews.(R.id.avatar, message)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(avatar)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setCustomContentView(remoteViews)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

}