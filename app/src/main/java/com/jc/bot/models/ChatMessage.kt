package com.jc.bot.models



data class ChatMessage(
    val username: String,
    val message: String,
    val image: Int,
    val isMe: Boolean = false,
    val sentAt: Long = System.currentTimeMillis(),
)