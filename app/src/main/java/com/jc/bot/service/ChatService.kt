package com.jc.bot.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import com.jc.bot.notification.Notifications

class ChatService : Service()   {

    companion object {
        const val CMD_GENERATE_MESSAGE = "generate_message"
        const val CMD_STOP_SERVICE = "stop_service"
        const val CMD_MSG = "command"
        const val BROADCAST_ID = "com.jc.chat.message"

    }

    private lateinit var notificationDecorator: Notifications
    private  lateinit var notificationMgr: NotificationManager

    override fun onCreate() {
        super.onCreate()

        notificationDecorator = Notifications(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(intent != null){
            val data = intent.extras
            handleData(data)
        }
        return START_STICKY
    }

    private fun handleData(data: Bundle?) {

        if(data == null) return
        if(!data.containsKey(CMD_MSG)) return

        when (data.getString(CMD_MSG)) {
            CMD_GENERATE_MESSAGE -> {
                generateMessages()
            }
            CMD_STOP_SERVICE -> {
                stopMessageService()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun stopMessageService(){

        val studentIdLast2Digits = "59"

        sendMessage("ChatBot Stopped: $studentIdLast2Digits")

        notificationDecorator.showNotification(
            "Shutdown",
            "ChatBot Stopped: $studentIdLast2Digits"
        )

    }
    private fun generateMessages(){
        var user = "Jovi"

        //generate message in sequence with a 1 second delay between each
        Thread {
            try {
                notificationDecorator.showNotification(
                    "New message",
                    "Hello $user",
                )
                sendMessage("Hello $user")
                Thread.sleep(1000)
                notificationDecorator.showNotification(
                    "New message",
                    "How are you? ",
                )
                sendMessage("How are you? ")
                Thread.sleep(1000)
                notificationDecorator.showNotification(
                    "New message",
                    "Good Bye $user!",
                )
                sendMessage("Good Bye $user!")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }

    fun sendMessage(message: String?) {
        val intent = Intent(ChatService.BROADCAST_ID)
        intent.putExtra("message", message)
        sendBroadcast(intent)
    }


}