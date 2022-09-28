package com.example.a7minworkout

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface historydao {
    @Insert
    suspend fun insert(historyentity: historyentity)

   @Query("SELECT * FROM `history-table`")
    fun fetchAlldates(): Flow<List<historyentity>>
}