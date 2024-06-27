package br.com.fgomes.cgd.room.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.fgomes.cgd.room.model.Punctuation.Companion.TABLE_NAME
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
data class Punctuation(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var name: String,
    var value: Long,
    var data: Date ): Parcelable {
    constructor( ): this(0, "", 0,  Date())

    companion object {
        const val TABLE_NAME = "punctuation"
    }
}