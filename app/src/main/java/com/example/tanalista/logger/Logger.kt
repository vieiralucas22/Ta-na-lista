package com.example.tanalista.logger

import android.util.Log

class Logger {

    companion object {

        private const val APP_TAG: String = "[TaNaLista] "

        fun d(classTag: String, message: String) {
            val tag = APP_TAG + classTag
            Log.d(tag, message)
        }

        fun e(exception: Exception, classTag: String, message: String) {
            val tag = APP_TAG + classTag
            val finalMessage = "\n\nMessage Exception: " + exception.message + "\n\n" + message
            Log.e(tag, finalMessage)
        }

        fun w(classTag: String, message: String) {
            val tag = APP_TAG + classTag
            Log.w(tag, message)
        }

        fun i(classTag: String, message: String) {
            val tag = APP_TAG + classTag
            Log.i(tag, message)
        }
    }
}