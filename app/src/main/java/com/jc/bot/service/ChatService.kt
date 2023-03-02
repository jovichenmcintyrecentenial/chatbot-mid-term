package com.jc.bot.service

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder

class ChatService : Service()   {

    override fun onCreate() {
        super.onCreate()
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
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}