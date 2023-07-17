package br.com.fgomes.cgd.data

import java.util.Date

data class Grupo(
    val idGrupo: Int,
    var nomeGrupo: String,
    var idUsuario: Int,
    var senhaGrupo: String,
    var tokenGrupo: String,
    var cadastradoGrupo: Date,
    var modificadoGrupo: Date,
    var listaPontuacao: List<Pontuacao>
    )
