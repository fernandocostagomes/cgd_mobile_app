package br.com.fgomes.cgd.updateproject.repository

import androidx.annotation.WorkerThread
import br.com.fgomes.cgd.updateproject.dao.GrupoUsuarioDao
import br.com.fgomes.cgd.updateproject.data.GrupoUsuario

class GrupoUsuarioRepository(private val grupoUsuarioDao: GrupoUsuarioDao){

    /**
     * Insere um GrupoUsuario no banco de dados.
     * @param grupoUsuario Objeto a ser inserido no banco de dados.
     * @return Long id do usuario inserido.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(grupoUsuario: GrupoUsuario): Long {
        return grupoUsuarioDao.insert(grupoUsuario)
    }

    /**
     * Deleta um GrupoUsuario no banco de dados.
     * @param grupoUsuario Objeto a ser deletado no banco de dados.
     * @return Boolean true se deletado com sucesso.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(grupoUsuario: GrupoUsuario): Boolean {
        return grupoUsuarioDao.delete(grupoUsuario)
    }

    /**
     * Atualiza um GrupoUsuario no banco de dados.
     * @param grupoUsuario Objeto a ser atualizado no banco de dados.
     * @return Boolean true se atualizado com sucesso.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(grupoUsuario: GrupoUsuario): Boolean {
        return grupoUsuarioDao.update(grupoUsuario)
    }

    /**
     * Retorna um GrupoUsuario.
     * @param idGrupoUsuario id do GrupoUsuario a ser retornado.
     * @return GrupoUsuario requisitado.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPorId(idGrupoUsuario: Int): GrupoUsuario {
        return grupoUsuarioDao.selectById(idGrupoUsuario)
    }

    /**
     * Retorna lista de GrupoUsuario por idGrupo.
     * @param idGrupo id do Grupo a ser retornado.
     * @return Lista de GrupoUsuario.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPorIdGrupo(idGrupoGrupoUsuario: Int): List<GrupoUsuario> {
        return grupoUsuarioDao.selectByIdGrupo(idGrupoGrupoUsuario)
    }

    /**
     * Retorna lista de GrupoUsuario por idUsuario.
     * @param idUsuario id do Usuario a ser retornado.
     * @return Lista de GrupoUsuario.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPorIdUsuario(idUsuarioGrupoUsuario: Int): List<GrupoUsuario> {
        return grupoUsuarioDao.selectByIdUsuario(idUsuarioGrupoUsuario)
    }

    /**
     * Retorna um GrupoUsuario por idGrupo e idUsuario.
     * @param idGrupo id do Grupo a ser retornado.
     * @param idUsuario id do Usuario a ser retornado.
     * @return GrupoUsuario requisitado.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPorIdGrupoAndIdUsuario(idGrupo: Int, idUsuario: Int): GrupoUsuario {
        return grupoUsuarioDao.selectByIdGrupoAndIdUsuario(idGrupo, idUsuario)
    }

    /**
     * Retorna lista observavel de todos os GrupoUsuario.
     * @return Lista de GrupoUsuario.
     */
    val listarTodos = grupoUsuarioDao.selectAll()


}