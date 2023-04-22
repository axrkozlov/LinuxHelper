package com.ldv.linuxhelper.db

import android.content.Context
import androidx.room.Room

class DbModule(context: Context) {

        private val database: TopicDatabase by lazy {
            Room.databaseBuilder(context, TopicDatabase::class.java, "topic_database")
                .fallbackToDestructiveMigration()
                .build()
        }

         fun provideDatabase() = database



}