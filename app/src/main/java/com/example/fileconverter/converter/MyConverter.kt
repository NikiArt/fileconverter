package com.example.fileconverter.converter

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.fileconverter.conversation.MyConversation
import io.reactivex.Completable

class MyConverter(private val context: Context) : ImageConverter {

    override fun convertJpegToPng(meta: MyConversation): Completable {
        return Completable.create {
            Thread.sleep(10000)
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(meta.getSrc()))
            bitmap.compress(
                Bitmap.CompressFormat.PNG,
                100,
                context.contentResolver.openOutputStream(Uri.parse(meta.getDat()))
            )
            Log.d("DDLog", "Converted!")
        }
    }
}