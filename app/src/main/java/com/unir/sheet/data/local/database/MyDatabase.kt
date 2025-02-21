package com.unir.sheet.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.RolCharacter
import com.unir.sheet.data.model.Skill
import com.unir.sheet.data.model.CharacterItemCrossRef
import com.unir.sheet.data.model.CharacterSkillCrossRef
import com.unir.sheet.data.model.CharacterSpellCrossRef
import com.unir.sheet.data.model.Spell

@Database(entities = [
    RolCharacter::class,
    Skill::class,
    Item::class,
    Spell::class,
    CharacterItemCrossRef::class,
    CharacterSpellCrossRef::class,
    CharacterSkillCrossRef::class,
], version = 18)
abstract class MyDatabase: RoomDatabase() {
    abstract fun getItemDao(): ItemDao
    abstract fun characterDao(): CharacterDao

    companion object {

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Aquí es donde creas la nueva tabla si no existe
                database.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS `character_item_cross_ref` (
                `characterId` INTEGER NOT NULL,
                `itemId` TEXT NOT NULL,
                PRIMARY KEY(`characterId`, `itemId`),
                FOREIGN KEY(`characterId`) REFERENCES `rolCharacterTable`(`id`) ON DELETE CASCADE,
                FOREIGN KEY(`itemId`) REFERENCES `itemTable`(`id`) ON DELETE CASCADE
            )
            """
                )
            }
        }


        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                )
                    //.addMigrations()
                    .fallbackToDestructiveMigration() // DESTRUYE LA BASE DE DATOS ANTERIOR AL MIGRAR
                    .build() // Remove addMigrations() if present
                INSTANCE= instance
                instance
            }
        }
    }
}