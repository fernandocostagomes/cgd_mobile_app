package br.com.fgomes.cgd.updateproject.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.fgomes.cgd.updateproject.entity.Grupo
import kotlinx.coroutines.flow.Flow

@Dao
interface GrupoDao  : BaseDao<Grupo>{
    @Query("SELECT * FROM Grupo WHERE idGrupo IN (:idGrupo)")
    fun selectById(idGrupo: Int): Grupo

    @Query("SELECT * FROM Grupo")
    fun selectAll(): Flow<List<Grupo>>
}