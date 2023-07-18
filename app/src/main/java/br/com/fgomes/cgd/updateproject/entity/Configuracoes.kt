package br.com.fgomes.cgd.updateproject.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Configuracoes(
    @PrimaryKey(autoGenerate = true)
    val idUsuario: Int,
    val codigoUsuario: Int,
    var nomeUsuario: String?
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    ){
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(idUsuario)
        dest.writeInt(codigoUsuario)
        dest.writeString(nomeUsuario)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR: Parcelable.Creator<Configuracoes> {
        override fun createFromParcel(parcel: Parcel): Configuracoes {
            return Configuracoes(parcel)
        }

        override fun newArray(size: Int): Array<Configuracoes?> {
            return arrayOfNulls(size)
        }
    }
}




