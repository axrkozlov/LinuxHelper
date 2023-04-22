package com.ldv.linuxhelper.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Topic::class], version = 1)
abstract class TopicDatabase : RoomDatabase() {

    abstract fun topicDao(): TopicDao

}