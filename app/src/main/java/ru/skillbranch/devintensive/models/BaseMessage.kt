package ru.skillbranch.devintensive.models

import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {

    abstract fun formatMessage():String

    companion object AbstractFactory{
        var lastId = -1
        fun makeMessage(from: User?, chat: Chat, date: Date = Date(), payload:Any?, type:String="text", isIncoming: Boolean = false):BaseMessage{
            lastId++
            return when(type){
                "image"-> {
                    val imageMessage = ImageMessage("$lastId", from, chat, date = date, image = payload as String, isIncoming = isIncoming)
                    println(imageMessage.formatMessage())
                    imageMessage
                }
                else -> {
                    val textMessage = TextMessage("$lastId", from, chat, date = date, text = payload as String, isIncoming = isIncoming)
                    println(textMessage.formatMessage())
                    textMessage
                }
            }
        }
    }
}