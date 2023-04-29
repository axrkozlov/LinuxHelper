package com.ldv.linuxhelper.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TipDao {

    @Query("SELECT * FROM tips_table ORDER BY number ASC")
    fun getTips(): Flow<List<Tip>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tipList: List<Tip>)

    @Update
    fun update(tip: Tip)

}