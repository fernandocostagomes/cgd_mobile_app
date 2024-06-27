package br.com.fgomes.cgd.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.fgomes.cgd.room.model.Game

/**
 * Criado por fernando.gomes em 25/06/2024.
 */
@Dao
interface GameDao {
    @Query("SELECT COUNT(*) FROM ${Game.TABLE_NAME}")
    fun count(): Int

    @Query("SELECT * FROM ${Game.TABLE_NAME}")
    fun selectAll(): List<Game>

    @Query("SELECT * FROM ${Game.TABLE_NAME} WHERE id LIKE :idgame")
    fun selectById(idgame: Long): Game

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(game: Game): Long

    @Update
    fun update(vararg game: Game): Int

    @Delete
    fun delete(game: Game)

    @Query("DELETE FROM ${Game.TABLE_NAME}")
    fun dropTable()
}