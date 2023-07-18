package br.com.fgomes.cgd.updateproject.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.fgomes.cgd.updateproject.entity.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao : BaseDao<Usuario>{
    @Query("SELECT * FROM Usuario WHERE idUsuario IN (:idUsuario)")
    fun selectById(idUsuario: Int): Usuario

    @Query("SELECT * FROM Usuario")
    fun selectAll(): Flow<List<Usuario>>
}

