package br.com.fgomes.cgd.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.fgomes.cgd.room.model.Tribe

/**
 * Criado por fernando.gomes em 25/06/2024.
 */
@Dao
interface TribeDao {
    @Query("SELECT COUNT(*) FROM ${Tribe.TABLE_NAME}")
    fun count(): Int

    @Query("SELECT * FROM ${Tribe.TABLE_NAME}")
    fun selectAll(): List<Tribe>

    @Query("SELECT * FROM ${Tribe.TABLE_NAME} WHERE id LIKE :id")
    fun selectById(id: Long): Tribe

    @Query("SELECT * FROM ${Tribe.TABLE_NAME} WHERE name LIKE :name")
    fun selectByName(name: String): Tribe

    @Query("SELECT * FROM ${Tribe.TABLE_NAME} WHERE idPlayer LIKE :idplayer")
    fun selectByPlayer(idplayer: Int): List<Tribe>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tribe: Tribe): Long

    @Update
    fun update(vararg tribe: Tribe): Int

    @Delete
    fun delete(tribe: Tribe)
}