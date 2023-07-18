package br.com.fgomes.cgd.updateproject.data

import androidx.room.Entity
import java.util.Date

@Entity
data class Permissoes(
    val idPermissoes: Int,
    val codigoPermissoes: Int,
    var nomePermissoes: String
    )
