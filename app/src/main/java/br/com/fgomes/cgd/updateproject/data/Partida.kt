package br.com.fgomes.cgd.updateproject.data

import androidx.room.Entity
import java.util.Date

@Entity
data class Partida(
    val idPartida: Int,
    val idUsuario1Partida: Int,
    val idUsuario2Partida: Int,
    val idUsuario3Partida: Int,
    val idUsuario4Partida: Int,
    var idGrupoPartida: Int,
    val idPontuacaoPartida: Int,
    val dataPartida: Date
    )
