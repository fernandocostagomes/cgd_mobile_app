package br.com.fgomes.cgd.data

import java.util.Date

data class GrupoUsuario(
    val idGrupoGrupoUsuario: Int,
    var idUsuarioGrupoUsuario: String,
    var listaPermissoes: List<Permissoes>
    )
