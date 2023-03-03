package com.jc.bot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.bot.databinding.ActivityChatBinding
import com.jc.bot.models.ChatMessage
import com.jc.bot.ui.adapter.ChatAdapter

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:ChatAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val listOfMessage:MutableList<ChatMessage> = ArrayList<ChatMessage>()

        listOfMessage.add(
            ChatMessage("Jovi",
                "Hello World",
                R.drawable.main_button_shape)
        )
        listOfMessage.add(
            ChatMessage("Jovi",
                "Hello World",
                R.drawable.main_button_shape)
        )
        listOfMessage.add(
            ChatMessage("Jovi",
                "Hello World",
                R.drawable.main_button_shape)
        )
        listOfMessage.add(
            ChatMessage("Jovi",
                "Cleared Reference was only reachable from finalizer (only reported once)",
                R.drawable.main_button_shape)
        )

        adapter = ChatAdapter(listOfMessage)
        recyclerView.adapter = adapter


    }
}