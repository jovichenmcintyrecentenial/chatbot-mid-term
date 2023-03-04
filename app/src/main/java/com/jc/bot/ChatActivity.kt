package com.jc.bot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.jc.bot.data.SharedPreferenceStore.USERNAME
import com.jc.bot.data.SharedPreferenceStore.loadData
import com.jc.bot.databinding.ActivityChatBinding
import com.jc.bot.models.ChatMessage
import com.jc.bot.models.MyConstants
import com.jc.bot.service.ChatService
import com.jc.bot.ui.adapter.ChatAdapter

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:ChatAdapter
    private lateinit var receiver: BroadcastReceiver
    private  var listOfMessage:MutableList<ChatMessage> = ArrayList<ChatMessage>()

    private lateinit var myServiceIntent:Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        myServiceIntent = Intent(applicationContext, ChatService::class.java)

        adapter = ChatAdapter(listOfMessage)
        recyclerView.adapter = adapter


        val rootView = binding.rootView

        // Scroll the RecyclerView to the bottom if detect change in layout
        // size which may indicate that the keybord is visible
        rootView.viewTreeObserver.addOnGlobalLayoutListener {

            if(adapter.itemCount > 3) {
                recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
            }
        }


        // Create the BroadcastReceiver
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val message = intent.getStringExtra("message")
                val chatMessage = Gson().fromJson(message,ChatMessage::class.java)
                listOfMessage.add(
                    chatMessage
                )
                adapter.notifyItemInserted(listOfMessage.count()-1)
            }
        }

        val filter = IntentFilter()
        filter.addAction(ChatService.BROADCAST_ID)
        registerReceiver(receiver, filter)

    }

    fun generateMessage(view: View) {

        var bundle = Bundle()
        bundle.putString(MyConstants.CHAT_NAME,"Bot")
        bundle.putInt(MyConstants.CHAT_AVATAR,R.drawable.chat_bot_avatar)
        bundle.putString(USERNAME,loadData(this,USERNAME,""))

        startService(ChatService.CMD_MSG, ChatService.CMD_GENERATE_MESSAGE,bundle)
    }

    fun stopService(view: View) {
        var bundle = Bundle()
        bundle.putString(MyConstants.CHAT_NAME,"Bot")
        bundle.putInt(MyConstants.CHAT_AVATAR,R.drawable.chat_bot_avatar)
        startService(ChatService.CMD_MSG, ChatService.CMD_STOP_SERVICE,bundle)
    }

    private fun startService(cmdMsg: String, cmdGenerateMessage: String,data:Bundle = Bundle()) {
        data.putString(cmdMsg, cmdGenerateMessage)
        myServiceIntent.putExtras(data)
        startService(myServiceIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(myServiceIntent)
        unregisterReceiver(receiver)
    }




}