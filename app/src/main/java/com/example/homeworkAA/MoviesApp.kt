package com.example.homeworkAA

import android.app.Application
import com.example.homeworkAA.di.Injection

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Injection.setup(this)
    }
}