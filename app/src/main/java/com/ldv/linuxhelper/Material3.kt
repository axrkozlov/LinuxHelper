package com.ldv.linuxhelper

import android.app.Application
import com.google.android.material.color.DynamicColors



class Material3 : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}