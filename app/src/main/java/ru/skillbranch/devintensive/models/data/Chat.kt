package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.ImageMessage
import ru.skillbranch.devintensive.models.TextMessage
import ru.skillbranch.devintensive.utils.Utils
import java.util.*
data class Chat(
    val id: String,
    val title: String,
    val members: List<User> = mutableListOf(),
    var messages: MutableList<BaseMessage> = mutableListOf(),
    var isArchived: Boolean = false
) {

    fun unreadableMessageCount(): Int {
        var count = 0
        messages.forEach{ if (!it.isReaded) count++ }
        return count
    }

    fun lastMessageDate(): Date? {
        return if (messages.lastOrNull() != null) messages.last().date
        else Date()
    }

    fun lastMessageShort(): Pair<String, String> {
        return if (messages.lastOrNull() != null) {
            val lastMessage = messages.last()
            val lastMessageAuthor = lastMessage.from.firstName!!
            when(lastMessage){
                is TextMessage -> lastMessage.text!! to lastMessageAuthor
                is ImageMessage -> "$lastMessageAuthor - отправил фото" to lastMessageAuthor
                else -> "" to ""
            }
        } else "Сообщений еще нет" to ""
    }

    companion object {
        fun archivedToChatItem(chats: List<Chat>): ChatItem {
            val lastChat =
                if (chats.none { it.unreadableMessageCount() > 0 }) chats.last() else
                    chats.filter { it.unreadableMessageCount() > 0 }.maxBy { it.lastMessageDate()!! }!!
            return ChatItem(
                id = "-1",
                initials = "",
                title = "Архив чатов",
                avatar = null,
                shortDescription = lastChat.lastMessageShort().first,
                lastMessageDate = lastChat.lastMessageDate()?.shortFormat(),
                messageCount = chats.sumBy { it.unreadableMessageCount() },
                chatType = ChatType.ARCHIVE,
                author = lastChat.lastMessageShort().second
            )
        }
    }

    private fun isSingle():Boolean = members.size == 1

    fun toChatItem(): ChatItem {
        return if (isSingle()){
            val user = members.first()
            ChatItem(
                id,
                user.avatar,
                Utils.toInitials(user.firstName, user.lastName) ?: "??",
                "${user.firstName?:""} ${user.lastName ?: ""}",
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                user.isOnline
            )
        } else {
            ChatItem(
                id,
                null,
                "",
                title,
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                false,
                ChatType.GROUP,
                lastMessageShort().second
            )
        }
    }
}


enum class ChatType{
    SINGLE,
    GROUP,
    ARCHIVE
}