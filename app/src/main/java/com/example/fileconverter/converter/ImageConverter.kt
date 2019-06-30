package com.example.fileconverter.converter

import com.example.fileconverter.conversation.MyConversation
import io.reactivex.Completable

interface ImageConverter {
    fun convertJpegToPng(meta: MyConversation): Completable
}