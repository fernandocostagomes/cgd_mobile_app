package br.com.fgomes.cgd.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.fgomes.cgd.data.Usuario

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(usuario: Usuario): Long

    @Query("SELECT * FROM Usuario")
    fun listaTodosUsuarios(): List<Usuario>

    @Query("SELECT * FROM Usuario WHERE idUsuario IN (:idUsuario)")
    fun getUsuarioPorId(idUsuario: IntArray): Usuario

    @Delete
    suspend fun deleteUsuario(idUsuario: Int)

    @Update
    suspend fun updateUsuario(usuario: Usuario)
}