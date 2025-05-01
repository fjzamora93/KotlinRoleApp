package com.unir.roleapp.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.unir.roleapp.auth.data.dao.UserDao
import com.unir.roleapp.auth.data.model.User
import com.unir.roleapp.character.data.dao.CharacterDao
import com.unir.roleapp.character.data.dao.ItemDao
import com.unir.roleapp.character.data.dao.SkillDao
import com.unir.roleapp.character.data.dao.SpellDao
import com.unir.roleapp.character.data.model.local.Item
import com.unir.roleapp.character.data.model.local.CharacterEntity
import com.unir.roleapp.character.data.model.local.CharacterItemCrossRef
import com.unir.roleapp.character.data.model.local.Skill
import com.unir.roleapp.character.data.model.local.CharacterSkillCrossRef
import com.unir.roleapp.character.data.model.local.Spell

@Database(entities = [
    User::class,
    CharacterEntity::class,
    Skill::class,
    Item::class,
    Spell::class,
    CharacterItemCrossRef::class,
    CharacterSkillCrossRef::class,
], version = 34)
abstract class MyDatabase: RoomDatabase() {
    abstract fun getItemDao(): ItemDao
    abstract fun characterDao(): CharacterDao
    abstract fun getSkillDao(): SkillDao
    abstract fun getSpellDao(): SpellDao
    abstract fun getUserDao(): UserDao

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
                INSTANCE = instance
                instance
            }
        }
    }
}