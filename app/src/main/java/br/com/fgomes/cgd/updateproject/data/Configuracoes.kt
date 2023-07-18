package br.com.fgomes.cgd.updateproject.data

import androidx.room.Entity
import java.util.Date

@Entity
data class Configuracoes(
    val idConfiguracoes: Int,
    val codigoConfiguracoes: Int,
    var nomeConfiguracoes: String
    )
