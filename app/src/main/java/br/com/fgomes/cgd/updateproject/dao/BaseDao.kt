package br.com.fgomes.cgd.updateproject.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T>{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: T): Long

    @Delete
    suspend fun delete(entity: T): Boolean

    @Update
    suspend fun update(entity: T): Boolean
}