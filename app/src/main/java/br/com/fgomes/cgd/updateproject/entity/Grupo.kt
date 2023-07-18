package br.com.fgomes.cgd.updateproject.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Grupo(
    @PrimaryKey(autoGenerate = true)
    val idGrupo: Int,
    var nomeGrupo: String,
    var idUsuario: Int,
    var senhaGrupo: String,
    var tokenGrupo: String,
    var cadastradoGrupo: Date,
    var modificadoGrupo: Date,
    var listaPontuacao: List<Pontuacao>
    )
