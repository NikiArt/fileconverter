package com.example.fileconverter.conversion

import android.net.Uri

class MyConversion(private val src: Uri, private val dat: Uri) : ConversionMeta {

    override fun getSrc(): String {
        return src.toString()
    }

    override fun getDat(): String {
        return dat.toString()
    }
}