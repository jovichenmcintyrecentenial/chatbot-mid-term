package com.jc.bot.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import com.google.gson.Gson
import com.jc.bot.R
import com.jc.bot.models.ChatMessage
import com.jc.bot.models.MyConstants
import com.jc.bot.models.MyConstants.ID_LAST_DIGIT
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
                generateMessages(data)
            }
            CMD_STOP_SERVICE -> {
                stopMessageService(data)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun stopMessageService(data: Bundle?){

        if(data == null) return

        val studentIdLast2Digits = ID_LAST_DIGIT
        val avatar = data.getInt(MyConstants.CHAT_AVATAR)
        val name = data.getString(MyConstants.CHAT_NAME,"")

        sendMessage(name,"ChatBot Stopped: $studentIdLast2Digits",avatar)

        notificationDecorator.showNotification(
            "New Message from $name",
            "ChatBot Stopped: $studentIdLast2Digits",
            data
        )

    }
    private fun generateMessages(data: Bundle?){

        if(data == null) return

        val user = data.getString(MyConstants.USER_NAME,"")
        val avatar = data.getInt(MyConstants.CHAT_AVATAR)
        val name = data.getString(MyConstants.CHAT_NAME,"")

        //generate message in sequence with a 1 second delay between each
        Thread {
            try {
                notificationDecorator.showNotification(
                    "New Message from $name",
                    "Hello $user",data
                )
                sendMessage(name,"Hello $user",avatar)
                Thread.sleep(1000)
                notificationDecorator.showNotification(
                    "New Message from $name",
                    "How are you? ",
                    data
                )
                sendMessage(name,"How are you? ",avatar)
                Thread.sleep(1000)
                notificationDecorator.showNotification(
                    "New Message from $name",
                    "Good Bye $user!",
                    data
                )
                sendMessage(name,"Good Bye $user!",avatar)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun sendMessage(name:String = "", msg: String, img:Int = 0) {
        val intent = Intent(BROADCAST_ID)
        var message = ChatMessage(name,msg,img)
        intent.putExtra("message", Gson().toJson( message))
        sendBroadcast(intent)
    }


}