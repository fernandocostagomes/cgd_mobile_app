package br.com.fgomes.cgd.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val idUsuario: Int,
    var nomeUsuario: String?,
    var emailUsuario: String?,
    var senhaUsuario: String?,
    var tokenUsuario: String?,
    var cadastradoUsuario: String?,
    var modificadoUsuario: String?
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idUsuario)
        parcel.writeString(nomeUsuario)
        parcel.writeString(emailUsuario)
        parcel.writeString(senhaUsuario)
        parcel.writeString(tokenUsuario)
        parcel.writeString(cadastradoUsuario)
        parcel.writeString(modificadoUsuario)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }
}
