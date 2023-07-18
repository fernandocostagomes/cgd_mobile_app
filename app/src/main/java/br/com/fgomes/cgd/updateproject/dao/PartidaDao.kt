package br.com.fgomes.cgd.updateproject.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.fgomes.cgd.updateproject.data.Partida
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidaDao : BaseDao<Partida> {
    @Query("SELECT * FROM Partida WHERE idPartida IN (:idPartida)")
    fun selectById(idPartida: Int): Partida

    @Query("SELECT * FROM Partida")
    fun selectAll(): Flow<List<Partida>>
}