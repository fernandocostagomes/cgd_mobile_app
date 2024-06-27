package br.com.fgomes.cgd.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.fgomes.cgd.room.model.Punctuation

/**
 * Criado por fernando.gomes em 25/06/2024.
 */
@Dao
interface PunctuationDao {
    @Query("SELECT COUNT(*) FROM ${Punctuation.TABLE_NAME}")
    fun count(): Int

    @Query("SELECT * FROM ${Punctuation.TABLE_NAME}")
    fun selectAll(): List<Punctuation>

    @Query("SELECT * FROM ${Punctuation.TABLE_NAME} WHERE id LIKE :idpunctuation")
    fun selectById(idpunctuation: Long): Punctuation

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(punctuation: Punctuation): Long

    @Update
    fun update(vararg punctuation: Punctuation): Int

    @Delete
    fun delete(punctuation: Punctuation)
}