package br.com.fgomes.cgd.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.fgomes.cgd.room.model.PlayerTribe

/**
 * Criado por fernando.gomes em 25/06/2024.
 */
@Dao
interface PlayerTribeDao {
    @Query("SELECT COUNT(*) FROM ${PlayerTribe.TABLE_NAME}")
    fun count(): Int

    @Query("SELECT * FROM ${PlayerTribe.TABLE_NAME}")
    fun selectAll(): List<PlayerTribe>

    @Query("SELECT * FROM ${PlayerTribe.TABLE_NAME} WHERE id LIKE :id")
    fun selectById(id: Long): PlayerTribe

    @Query("SELECT * FROM ${PlayerTribe.TABLE_NAME} WHERE idPlayer LIKE :idplayer")
    fun selectByPlayer(idplayer: Int): PlayerTribe

    @Query("SELECT * FROM ${PlayerTribe.TABLE_NAME} WHERE idTribe LIKE :idtribe")
    fun selectByTribe(idtribe: Int): PlayerTribe

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(player: PlayerTribe): Long

    @Update
    fun update(vararg player: PlayerTribe): Int

    @Delete
    fun delete(player: PlayerTribe)
}