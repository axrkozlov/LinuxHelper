package com.ldv.linuxhelper

import android.app.Application
import com.ldv.linuxhelper.di.coreModule
import com.google.android.material.color.DynamicColors
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(listOf(coreModule))
        }
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}