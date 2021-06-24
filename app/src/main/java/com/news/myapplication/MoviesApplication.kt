package com.news.myapplication

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

const val FIRST_PAGE = 1
const val MOVIES_PER_PAGE = 20

class MoviesApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        var context: Context? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}