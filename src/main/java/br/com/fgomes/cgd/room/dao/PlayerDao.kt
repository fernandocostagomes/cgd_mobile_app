package br.com.fgomes.cgd.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.fgomes.cgd.room.model.Player

/**
 * Criado por fernando.gomes em 25/06/2024.
 */
@Dao
interface PlayerDao {
    @Query("SELECT COUNT(*) FROM ${Player.TABLE_NAME}")
    fun count(): Int

    @Query("SELECT * FROM ${Player.TABLE_NAME}")
    fun selectAll(): List<Player>

    @Query("SELECT * FROM ${Player.TABLE_NAME} WHERE id LIKE :idplayer")
    fun selectById(idplayer: Long): Player

    @Query("SELECT * FROM ${Player.TABLE_NAME} WHERE name LIKE :nameplayer")
    fun selectByName(nameplayer: String): Player

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(player: Player): Long

    @Update
    fun update(vararg player: Player): Int

    @Delete
    fun delete(player: Player)
}