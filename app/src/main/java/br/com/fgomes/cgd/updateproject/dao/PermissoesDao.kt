package br.com.fgomes.cgd.updateproject.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.fgomes.cgd.updateproject.data.Permissoes
import kotlinx.coroutines.flow.Flow

@Dao
interface PermissoesDao : BaseDao<Permissoes>{

    @Query("SELECT * FROM Permissoes WHERE idPermissoes = :id")
    fun selectById(id: Int): Permissoes

    @Query("SELECT * FROM Permissoes")
    fun selectAll(): Flow<List<Permissoes>>
}