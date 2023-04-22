package com.ldv.linuxhelper.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topics_table")
data class Topic(
    @PrimaryKey @ColumnInfo(name = "number") val number: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "subtitle") val subtitle: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "isFavourite") var isFavourite: Boolean,
    @ColumnInfo(name = "likeCount") var likeCount: Long,

)