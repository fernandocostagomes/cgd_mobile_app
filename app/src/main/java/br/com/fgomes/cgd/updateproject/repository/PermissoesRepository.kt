package br.com.fgomes.cgd.updateproject.repository

import androidx.annotation.WorkerThread
import br.com.fgomes.cgd.updateproject.dao.PermissoesDao
import br.com.fgomes.cgd.updateproject.data.Permissoes

class PermissoesRepository(private val permissoesDao: PermissoesDao) {

    /**
     * Insere um registro na tabela Permissoes.
     * @param permissoes objeto Permissoes.
     * @return id do registro inserido.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(permissoes: Permissoes): Long {
        return permissoesDao.insert(permissoes)
    }

    /**
     * Deleta um registro da tabela Permissoes.
     * @param permissoes objeto Permissoes.
     * @return true se deletou, false se não deletou.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(permissoes: Permissoes): Boolean {
        return permissoesDao.delete(permissoes)
    }

    /**
     * Atualiza um registro da tabela Permissoes.
     * @param permissoes objeto Permissoes.
     * @return true se atualizou, false se não atualizou.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(permissoes: Permissoes): Boolean {
        return permissoesDao.update(permissoes)
    }

    /**
     * Retorna um registro da tabela Permissoes.
     * @param idPermissoes Int.
     * @return objeto Permissoes.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPorId(idPermissoes: Int): Permissoes {
        return permissoesDao.selectById(idPermissoes)
    }

    /**
     * Retorna todos os registros da tabela Permissoes.
     * @return lista observavel de objetos Permissoes.
     */
    val listarTodas = permissoesDao.selectAll()
}