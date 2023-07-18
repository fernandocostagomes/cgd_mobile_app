package br.com.fgomes.cgd.updateproject.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.fgomes.cgd.updateproject.data.Configuracoes
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfiguracoesDao : BaseDao<Configuracoes> {

    @Query("SELECT * FROM Configuracoes WHERE idConfiguracoes IN (:idConfiguracoes)")
    fun selectById(idConfiguracoes: Int): Configuracoes

    @Query("SELECT * FROM Configuracoes")
    fun selectAll(): Flow<List<Configuracoes>>
}