package com.example.tanalista.logger

import android.util.Log

object Logger {

    private const val APP_TAG = "[TaNaLista]"
    private fun buildTag(tag: String) = "$APP_TAG $tag"

    fun d(tag: String, message: String) {
        
        Log.d(buildTag(tag), message)
    }

    fun e(exception: Exception, tag: String, message: String) {
        
        val finalMessage = "\n\nMessage Exception: " + exception.message + "\n\n" + message
        Log.e(buildTag(tag), finalMessage)
    }

    fun w(tag: String, message: String) {
        
        Log.w(buildTag(tag), message)
    }

    fun i(tag: String, message: String) {
        
        Log.i(buildTag(tag), message)
    }
}