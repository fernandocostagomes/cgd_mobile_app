package br.com.fgomes.cgd.room.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

import java.util.Date

/**
 * Criado por fernando.gomes em 25/06/2024.
 */
@Entity(
    tableName = Tribe.TABLE_NAME,
    indices = [Index(value = ["id"], unique = true)])
@Parcelize
data class Tribe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var name: String = "",
    var password: String = "",
    var punctuation: List<Long> = listOf(),
    var data: Date,
    var idPlayer: Long = 0): Parcelable {
    constructor( ): this(0, "", "", listOf(), Date(), 0)

    companion object {
        const val TABLE_NAME = "tribe"
    }
}