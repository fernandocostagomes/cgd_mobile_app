package br.com.fgomes.cgd.updateproject.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.fgomes.cgd.updateproject.data.Pontuacao
import kotlinx.coroutines.flow.Flow

@Dao
interface PontuacaoDao : BaseDao<Pontuacao>{

    @Query("SELECT * FROM Pontuacao WHERE idPontuacao = :id")
    fun selectById(id: Int): Pontuacao

    @Query("SELECT * FROM Pontuacao")
    fun selectAll(): Flow<List<Pontuacao>>
}