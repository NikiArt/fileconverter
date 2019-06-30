package com.example.fileconverter.conversation

import android.net.Uri

class MyConversation(private val src: Uri, private val dat: Uri) : ConversationMeta {

    override fun getSrc(): String {
        return src.toString()
    }

    override fun getDat(): String {
        return dat.toString()
    }
}