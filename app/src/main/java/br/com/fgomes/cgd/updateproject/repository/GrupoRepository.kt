package br.com.fgomes.cgd.updateproject.repository

import androidx.annotation.WorkerThread
import br.com.fgomes.cgd.updateproject.dao.GrupoDao
import br.com.fgomes.cgd.updateproject.entity.Grupo

class GrupoRepository(private val grupoDao: GrupoDao) {

    /**
     * Insere um novo registro na tabela Grupo.
     * @param grupo Objeto Grupo.
     * @return id do registro inserido.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(grupo: Grupo): Long {
        return grupoDao.insert(grupo)
    }

    /**
     * Deleta um registro da tabela Grupo.
     * @param grupo Objeto Grupo.
     * @return true se deletou, false se não deletou.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(grupo: Grupo): Boolean {
        return grupoDao.delete(grupo)
    }

    /**
     * Atualiza um registro da tabela Grupo.
     * @param grupo Objeto Grupo.
     * @return true se atualizou, false se não atualizou.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(grupo: Grupo): Boolean {
        return grupoDao.update(grupo)
    }

    /**
     * Retorna um registro da tabela Grupo.
     * @param idGrupo Int.
     * @return Objeto Grupo.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getGrupoPorId(idGrupo: Int): Grupo {
        return grupoDao.selectById(idGrupo)
    }

    /**
     * Retorna todos os registros da tabela Grupo.
     * @return Lista observavel de Grupo.
     */
    val listaTodosGrupos = grupoDao.selectAll()
}