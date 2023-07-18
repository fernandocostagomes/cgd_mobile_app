package br.com.fgomes.cgd.updateproject.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fgomes.cgd.updateproject.dao.ConfiguracoesDao
import br.com.fgomes.cgd.updateproject.dao.GrupoDao
import br.com.fgomes.cgd.updateproject.dao.GrupoUsuarioDao
import br.com.fgomes.cgd.updateproject.dao.PartidaDao
import br.com.fgomes.cgd.updateproject.dao.PermissoesDao
import br.com.fgomes.cgd.updateproject.dao.PontuacaoDao
import br.com.fgomes.cgd.updateproject.dao.UsuarioDao
import br.com.fgomes.cgd.updateproject.data.Configuracoes
import br.com.fgomes.cgd.updateproject.data.Grupo
import br.com.fgomes.cgd.updateproject.data.GrupoUsuario
import br.com.fgomes.cgd.updateproject.data.Partida
import br.com.fgomes.cgd.updateproject.data.Permissoes
import br.com.fgomes.cgd.updateproject.data.Pontuacao
import br.com.fgomes.cgd.updateproject.data.Usuario
import kotlin.reflect.KClass

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf<KClass<*>>(
    Configuracoes::class,
    Grupo::class,
    GrupoUsuario::class,
    Partida::class,
    Permissoes::class,
    Pontuacao::class,
    Usuario::class
), version = 1, exportSchema = false)
public abstract class CgdRoomDatabase : RoomDatabase() {

    abstract fun configuracoesDao(): ConfiguracoesDao
    abstract fun GrupoDao(): GrupoDao
    abstract fun GrupoUsuarioDao(): GrupoUsuarioDao
    abstract fun PartidaDao(): PartidaDao
    abstract fun PermissoesDao(): PermissoesDao
    abstract fun PontuacaoDao(): PontuacaoDao
    abstract fun UsuarioDao(): UsuarioDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CgdRoomDatabase? = null

        fun getDatabase(context: Context): CgdRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CgdRoomDatabase::class.java,
                    "cgd_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

