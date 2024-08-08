package com.op.movies.util

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun Uri.toFile(context: Context): File? {
    val contentResolver = context.contentResolver
    val file = File("${context.cacheDir.absoluteFile}${System.currentTimeMillis()}.jpeg")
    file.createNewFile()
    try {
        val inputStream = contentResolver.openInputStream(this)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return file
    } catch (e: IOException) {
        Log.e("debug", e.localizedMessage, e)
    }
    return null
}