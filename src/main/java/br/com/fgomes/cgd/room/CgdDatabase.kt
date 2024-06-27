package br.com.fgomes.cgd.room
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.fgomes.cgd.room.converter.DateConverter
import br.com.fgomes.cgd.room.converter.IntArrayConverter
import br.com.fgomes.cgd.room.converter.ListLongConverter
import br.com.fgomes.cgd.room.dao.GameDao
import br.com.fgomes.cgd.room.dao.PermissionDao
import br.com.fgomes.cgd.room.dao.PlayerDao
import br.com.fgomes.cgd.room.dao.PlayerTribeDao
import br.com.fgomes.cgd.room.dao.PunctuationDao
import br.com.fgomes.cgd.room.dao.TribeDao
import br.com.fgomes.cgd.room.model.Game
import br.com.fgomes.cgd.room.model.Permission
import br.com.fgomes.cgd.room.model.Player
import br.com.fgomes.cgd.room.model.PlayerTribe
import br.com.fgomes.cgd.room.model.Punctuation
import br.com.fgomes.cgd.room.model.Tribe

/**
 * Criado por fernando.gomes em 25/06/2024.
 */
@Database(
    version = 1,
    entities = [
        Player::class,
        Tribe::class,
        PlayerTribe::class,
        Punctuation::class,
        Game::class,
        Permission::class],
    exportSchema = true )
@TypeConverters(
    DateConverter::class,
    IntArrayConverter::class,
    ListLongConverter::class)
abstract class CgdDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

    abstract fun permissionDao(): PermissionDao

    abstract fun playerDao(): PlayerDao

    abstract fun playerTribeDao(): PlayerTribeDao

    abstract fun punctuationDao(): PunctuationDao

    abstract fun tribeDao(): TribeDao

    companion object {

        private const val DATABASE_NAME = "cgd_database.db"
        fun getInstance( pContext: Context ): CgdDatabase {
            return Room.databaseBuilder(
                pContext,
                CgdDatabase::class.java,
                DATABASE_NAME
            )
                .allowMainThreadQueries()
                // Descomentar a linha abaixo para adicionar as migrations, quando houver versoes
                // em que a estrutura do banco de dados foi alterada.
                //.addMigrations( *ALL_MIGRATIONS )
                .build()
        }
    }
}