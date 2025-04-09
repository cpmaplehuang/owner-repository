package com.example.unscramble

import android.app.Application

class UnscrambleApplication : Application() {

    internal var onCreateWasCalled = false

    override fun onCreate() {
        super.onCreate()
        this.onCreateWasCalled = true
    }
}