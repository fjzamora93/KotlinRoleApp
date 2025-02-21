package com.unir.sheet.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.unir.sheet.data.local.database.CharacterDao
import com.unir.sheet.data.model.Skill
import com.unir.sheet.domain.repository.SkillRepository
import java.io.IOException
import javax.inject.Inject


class SkillRepository  @Inject constructor (
    private val characterDao: CharacterDao,
) : SkillRepository {

    override suspend fun readFromJson(context: Context): List<Skill> {
        val jsonString: String
        try {
            jsonString = context.assets.open("skills.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ex: IOException) {
            ex.printStackTrace()
            return emptyList() // Si hay un error, devuelve una lista vacía
        }

        // Convertir el JSON en una lista de objetos Ejemplo
        val listType = object : TypeToken<List<Skill>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }


    override suspend fun fetchSkillsFromCharacter(
        characterId: Int,
        skillId: Int,
    ) : List<Skill> {
        println("Fetching skills from character with ID: $characterId and skill ID: $skillId")
        return emptyList()
    }
}