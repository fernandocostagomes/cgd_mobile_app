package br.com.fgomes.cgd.updateproject.repository

import androidx.annotation.WorkerThread
import br.com.fgomes.cgd.updateproject.dao.UsuarioDao
import br.com.fgomes.cgd.updateproject.entity.Usuario

class UsuarioRepository(private val usuarioDao: UsuarioDao) {
    /**
     * Insere um registro na tabela Usuario.
     * @param usuario objeto Usuario.
     * @return id do registro inserido.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(usuario: Usuario): Long {
        return usuarioDao.insert(usuario)
    }

    /**
     * Deleta um registro da tabela Usuario.
     * @param usuario objeto Usuario.
     * @return true se deletou, false se não deletou.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(usuario: Usuario): Boolean {
        return usuarioDao.delete(usuario)
    }

    /**
     * Atualiza um registro da tabela Usuario.
     * @param usuario objeto Usuario.
     * @return true se atualizou, false se não atualizou.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(usuario: Usuario): Boolean {
        return usuarioDao.update(usuario)
    }

    /**
     * Retorna um registro da tabela Usuario.
     * @param idUsuario Int.
     * @return objeto Usuario.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPorId(idUsuario: Int): Usuario {
        return usuarioDao.selectById(idUsuario)
    }

    /**
     * Retorna todos os registros da tabela Usuario.
     * @return lista observavel de objetos Usuario.
     */
    val listarTodos = usuarioDao.selectAll()
}