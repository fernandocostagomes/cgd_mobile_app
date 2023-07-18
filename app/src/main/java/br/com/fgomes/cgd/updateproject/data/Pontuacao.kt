package br.com.fgomes.cgd.updateproject.data

import androidx.room.Entity
import java.util.Date

@Entity
data class Pontuacao(
    val idPontuacao: Int,
    var nomePontuacao: String,
    var valorPontuacao: Int
    )
