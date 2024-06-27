package br.com.fgomes.cgd.room.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.fgomes.cgd.room.model.PlayerTribe.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize
import java.security.Permission
import java.util.Date

/**
 * Criado por fernando.gomes em 25/06/2024.
 */
@Entity(
    tableName = TABLE_NAME,
    indices = [Index(value = ["id"], unique = true)]
)
@Parcelize
data class PlayerTribe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var idPlayer: Long,
    var idTribe: Long,
    var insertData: Date,
    var permission: List<Long>,
    var data: Date ): Parcelable {
    constructor( ): this(0, 0, 0, Date(), listOf(), Date())

    companion object {
        const val TABLE_NAME = "playertribe"
    }
}
