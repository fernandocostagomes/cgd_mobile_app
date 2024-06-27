package br.com.fgomes.cgd.room.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.fgomes.cgd.room.model.Game.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize
import java.util.Date

/**
 * Criado por fernando.gomes em 25/06/2024.
 */
@Entity(
    tableName = TABLE_NAME,
    indices = [Index(value = ["id"], unique = true)]
)
@Parcelize
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var idtribe: Long,
    var idwinpri: Long,
    var idwinsec: Long,
    var idlosepri: Long,
    var idlosesec: Long,
    var qtd: Long,
    var data: Date ): Parcelable {
    constructor( ): this(0, 0, 0, 0, 0, 0, 0, Date())

    companion object {
        const val TABLE_NAME = "game"
    }
}
