package com.unir.sheet.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.unir.sheet.data.local.dao.CharacterDao
import com.unir.sheet.data.local.dao.ItemDao
import com.unir.sheet.data.local.dao.SkillDao
import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.Skill
import com.unir.sheet.data.model.CharacterWithItems
import com.unir.sheet.data.model.CharacterSkillCrossRef
import com.unir.sheet.data.model.CharacterSpellCrossRef
import com.unir.sheet.data.model.Spell

@Database(entities = [
    CharacterEntity::class,
    Skill::class,
    Item::class,
    Spell::class,
    CharacterSpellCrossRef::class,
    CharacterSkillCrossRef::class,
], version = 22)
abstract class MyDatabase: RoomDatabase() {
    abstract fun getItemDao(): ItemDao
    abstract fun characterDao(): CharacterDao
    abstract fun getSkillDao(): SkillDao

    companion object {

        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE= instance
                instance
            }
        }
    }
}