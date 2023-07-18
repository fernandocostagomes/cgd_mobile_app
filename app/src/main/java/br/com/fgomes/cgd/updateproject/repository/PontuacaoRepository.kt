package br.com.fgomes.cgd.updateproject.repository

import androidx.annotation.WorkerThread
import br.com.fgomes.cgd.updateproject.dao.PontuacaoDao
import br.com.fgomes.cgd.updateproject.entity.Pontuacao

class PontuacaoRepository(private val pontuacaoDao: PontuacaoDao) {

    /**
     * Insere um registro na tabela Pontuacao.
     * @param pontuacao objeto Pontuacao.
     * @return id do registro inserido.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(pontuacao: Pontuacao): Long {
        return pontuacaoDao.insert(pontuacao)
    }

    /**
     * Deleta um registro da tabela Pontuacao.
     * @param pontuacao objeto Pontuacao.
     * @return true se deletou, false se não deletou.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(pontuacao: Pontuacao): Boolean {
        return pontuacaoDao.delete(pontuacao)
    }

    /**
     * Atualiza um registro da tabela Pontuacao.
     * @param pontuacao objeto Pontuacao.
     * @return true se atualizou, false se não atualizou.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(pontuacao: Pontuacao): Boolean {
        return pontuacaoDao.update(pontuacao)
    }

    /**
     * Retorna um registro da tabela Pontuacao.
     * @param idPontuacao Int.
     * @return objeto Pontuacao.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPorId(idPontuacao: Int): Pontuacao {
        return pontuacaoDao.selectById(idPontuacao)
    }

    /**
     * Retorna todos os registros da tabela Pontuacao.
     * @return lista observavel de objetos Pontuacao.
     */
    val listarTodas = pontuacaoDao.selectAll()
}