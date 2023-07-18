package br.com.fgomes.cgd.updateproject.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pontuacao(
    @PrimaryKey(autoGenerate = true)
    val idPontuacao: Int,
    var nomePontuacao: String,
    var valorPontuacao: Int
    )
