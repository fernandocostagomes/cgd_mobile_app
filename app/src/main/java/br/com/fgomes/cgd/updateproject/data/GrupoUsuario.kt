package br.com.fgomes.cgd.updateproject.data

import androidx.room.Entity

@Entity
data class GrupoUsuario(
    val idGrupoUsuario: Int,
    val idGrupoGrupoUsuario: Int,
    var idUsuarioGrupoUsuario: String,
    var listaPermissoes: List<Permissoes>
    )
