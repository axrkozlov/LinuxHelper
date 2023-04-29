package com.ldv.linuxhelper.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topics_table")
class Topic(
    @PrimaryKey @ColumnInfo(name = "number") val number: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "subtitle") val subtitle: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "isFavourite") var isBookmarked: Boolean,
    @ColumnInfo(name = "likeCount") var likeCount: Long,
    @ColumnInfo(name = "topicParts") var topicParts: List<TopicPart>,


    ) {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

}

class TopicPart(
    val command:String,
    val description: String,
    val example:String,
    var isBookmarked: Boolean = false
){
    override fun toString(): String = command + "\n" + description+ "\n" + example
}
