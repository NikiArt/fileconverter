package com.example.fileconverter.converter

import com.example.fileconverter.conversion.MyConversion
import io.reactivex.Completable

interface ImageConverter {
    fun convertJpegToPng(meta: MyConversion): Completable
}