package br.com.fgomes.cgd.updateproject.data

import androidx.room.Entity
import java.util.Date

@Entity
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
