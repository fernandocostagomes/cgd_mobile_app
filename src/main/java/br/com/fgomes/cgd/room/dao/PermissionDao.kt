package br.com.fgomes.cgd.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.fgomes.cgd.room.model.Permission

/**
 * Criado por fernando.gomes em 26/06/2024.
 */
@Dao
interface PermissionDao {
    @Query("SELECT COUNT(*) FROM ${Permission.TABLE_NAME}")
    fun count(): Int

    @Query("SELECT * FROM ${Permission.TABLE_NAME}")
    fun selectAll(): List<Permission>

    @Query("SELECT * FROM ${Permission.TABLE_NAME} WHERE id LIKE :idpermission")
    fun selectById(idpermission: Long): Permission

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(permission: Permission): Long

    @Update
    fun update(vararg permission: Permission): Int

    @Delete
    fun delete(permission: Permission)
}