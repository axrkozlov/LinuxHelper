package com.ldv.linuxhelper.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tips_table")
class Tip(
    @PrimaryKey @ColumnInfo(name = "number") val number: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "subtitle") val subtitle: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "isFavourite") var isBookmarked: Boolean,
    @ColumnInfo(name = "likeCount") var likeCount: Long,


    ) {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}