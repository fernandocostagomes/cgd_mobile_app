package br.com.fgomes.cgd.updateproject.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GrupoUsuario(
    @PrimaryKey(autoGenerate = true)
    val idGrupoUsuario: Int,
    val idGrupoGrupoUsuario: Int,
    var idUsuarioGrupoUsuario: String,
    var listaPermissoes: List<Permissoes>
    )
