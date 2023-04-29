package com.ldv.linuxhelper.db

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(entities = [Topic::class,Tip::class], version = 1)
@TypeConverters(ArrayListConverter::class)
abstract class TopicDatabase : RoomDatabase() {

    abstract fun topicDao(): TopicDao
    abstract fun tipDao(): TipDao

}

class ArrayListConverter {

    @TypeConverter
    fun toTopicPartArrayList(value: List<TopicPart>): String {
//        Log.i("TAG", "toTopicPartArrayList: $value")
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromTopicPartArrayList(value: String): List<TopicPart> {
        return try {
//            Log.i("TAG", "toTopicPartArrayList: $value")
            val itemType = object : TypeToken<List<TopicPart>>() {}.type
            Gson().fromJson<List<TopicPart>>(value,itemType) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}