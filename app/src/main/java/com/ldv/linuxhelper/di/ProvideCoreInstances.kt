package com.ldv.linuxhelper.di

import android.content.Context
import com.ldv.linuxhelper.db.DbModule

class ProvideCoreInstances(private val context: Context) {

//    fun provideCloudModule(): CloudModule

//    fun provideDbModule(): DbModule

//    class Release : ProvideCoreInstances {

//        override fun provideCloudModule() = CloudModule.Release()

         fun provideDbModule() = DbModule(context)



}