package br.com.fgomes.cgd.updateproject.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.fgomes.cgd.updateproject.entity.GrupoUsuario
import kotlinx.coroutines.flow.Flow

@Dao
interface GrupoUsuarioDao  : BaseDao<GrupoUsuario>{
    @Query("SELECT * FROM GrupoUsuario WHERE idGrupoUsuario = :idGrupoUsuario")
    fun selectById(idGrupoUsuario: Int): GrupoUsuario

    @Query("SELECT * FROM GrupoUsuario WHERE idGrupoGrupoUsuario = :idGrupo")
    fun selectByIdGrupo(idGrupo: Int): List<GrupoUsuario>

    @Query("SELECT * FROM GrupoUsuario WHERE idUsuarioGrupoUsuario = :idUsuarioGrupoUsuario")
    fun selectByIdUsuario(idUsuarioGrupoUsuario: Int): List<GrupoUsuario>

    @Query("SELECT * FROM GrupoUsuario WHERE idGrupoGrupoUsuario = :idGrupo AND idUsuarioGrupoUsuario = :idUsuario")
    fun selectByIdGrupoAndIdUsuario(idGrupo: Int, idUsuario: Int): GrupoUsuario

    @Query("SELECT * FROM GrupoUsuario")
    fun selectAll(): Flow<List<GrupoUsuario>>
}
