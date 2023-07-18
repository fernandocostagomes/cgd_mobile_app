package br.com.fgomes.cgd.updateproject.repository

import androidx.annotation.WorkerThread
import br.com.fgomes.cgd.updateproject.dao.ConfiguracoesDao
import br.com.fgomes.cgd.updateproject.entity.Configuracoes

class ConfiguracoesRepository(private val configuracoesDao: ConfiguracoesDao) {

    /**
     * Insere um novo registro na tabela Configuracoes
     * @param configuracoes Objeto Configuracoes
     * @return id do registro inserido
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(configuracoes: Configuracoes) {
        configuracoesDao.insert(configuracoes)
    }

    /**
     * Deleta um registro da tabela Configuracoes
     * @param configuracoes Objeto Configuracoes
     * @return true se deletou, false se não deletou
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(configuracoes: Configuracoes): Boolean {
        return configuracoesDao.delete(configuracoes)
    }

    /**
     * Atualiza um registro da tabela Configuracoes
     * @param configuracoes Objeto Configuracoes
     * @return true se atualizou, false se não atualizou
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(configuracoes: Configuracoes): Boolean {
        return configuracoesDao.update(configuracoes)
    }

    /**
     * Retorna um registro da tabela Configuracoes
     * @param idConfiguracoes Int
     * @return Objeto Configuracoes
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPorId(idConfiguracoes: Int): Configuracoes {
        return configuracoesDao.selectById(idConfiguracoes)
    }

    /**
     * Retorna todos os registros da tabela Configuracoes
     * @return Lista observavel de Configuracoes
     */
    val listarTodas = configuracoesDao.selectAll()
}
