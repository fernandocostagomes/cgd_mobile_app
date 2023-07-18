package br.com.fgomes.cgd.updateproject.repository

import androidx.annotation.WorkerThread
import br.com.fgomes.cgd.updateproject.dao.PartidaDao
import br.com.fgomes.cgd.updateproject.data.Partida

class PartidaRepository(private val partidaDao: PartidaDao) {

    /**
     * Insere um novo registro na tabela Partida.
     * @param partida Objeto Partida.
     * @return id do registro inserido.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(partida: Partida): Long {
        return partidaDao.insert(partida)
    }

    /**
     * Deleta um registro da tabela Partida.
     * @param partida Objeto Partida.
     * @return true se deletou, false se não deletou.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(partida: Partida): Boolean {
        return partidaDao.delete(partida)
    }

    /**
     * Atualiza um registro da tabela Partida.
     * @param partida Objeto Partida.
     * @return true se atualizou, false se não atualizou.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(partida: Partida): Boolean {
        return partidaDao.update(partida)
    }

    /**
     * Retorna um registro da tabela Partida.
     * @param idPartida Int.
     * @return Objeto Partida.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPartidaPorId(idPartida: Int): Partida {
        return partidaDao.selectById(idPartida)
    }

    /**
     * Retorna todos os registros da tabela Partida.
     * @return Lista observavel de Partida.
     */
    val listarTodasPartidas = partidaDao.selectAll()
}