package br.com.fgomes.cgd.updateproject.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Permissoes(
    @PrimaryKey(autoGenerate = true)
    val idPermissoes: Int,
    val codigoPermissoes: Int,
    var nomePermissoes: String
    )
