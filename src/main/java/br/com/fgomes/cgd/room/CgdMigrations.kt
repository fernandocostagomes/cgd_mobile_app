package br.com.fgomes.cgd.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Criado por fernando.gomes em 05/06/2024.
 */
internal object CgdMigrations {
    private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS ...")
            database.execSQL("INSERT INTO ...")
            database.execSQL("UPDATE ...")
            database.execSQL("DROP TABLE ...")
            database.execSQL("ALTER TABLE ...")
        }
    }

    //Um array de migrations.
    internal val ALL_MIGRATIONS = arrayOf(MIGRATION_1_2)
}

