package com.unir.sheet.data.repository

import android.content.Context
import com.unir.sheet.data.local.dao.CharacterDao
import com.unir.sheet.data.local.dao.ItemDao
import com.unir.sheet.data.local.dao.SkillDao
import com.unir.sheet.data.model.CharacterSkillCrossRef
import com.unir.sheet.data.model.Skill
import com.unir.sheet.data.remote.model.toSkill
import com.unir.sheet.data.remote.service.ApiService
import com.unir.sheet.domain.repository.SkillRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class SkillRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val skillDao: SkillDao
) : SkillRepository {

    override suspend fun getAllSkills(): Result<List<Skill>> {
        return try {
            val response = apiService.getAllSkills()
            if (response.isSuccessful) {
                val skills = response.body()?.map { it.toSkill() } ?: emptyList()
                skillDao.insertAll(skills)
                Result.success(skills)
            } else {
                Result.failure(Exception("Error al obtener habilidades: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSkillsFromCharacter(characterId: Long): Result<List<Skill>> {
        return try {
            val response = apiService.getSkillsByCharacterId(characterId)
            if (response.isSuccessful) {
                val skills = response.body()?.map { it.toSkill() } ?: emptyList()
                Result.success(skills)
            } else {
                Result.failure(Exception("Error al obtener habilidades: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun addDefaultSkills(characterId: Long, skillIds: List<Int>): Result<List<Skill>> {



        val defaultSkills : List<Skill> =  skillDao.updateCharacterSkills(characterId, skillIds)
        println("Asignando habilidades $defaultSkills al personaje $characterId")
        CoroutineScope(Dispatchers.IO).launch {
            apiService.addDefaultSkills(characterId, skillIds)
        }
        return try {
            Result.success(defaultSkills)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    override suspend fun addSkillToCharacter(characterId: Long, skillId: Int): Result<Unit> {
        return try {
            val response = apiService.addSkillToCharacter(characterId, skillId.toLong())
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al añadir habilidad: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteSkillFromCharacter(characterId: Long, skillId: Int): Result<Unit> {
        return try {
            val response = apiService.deleteSkillFromCharacter(characterId.toLong(), skillId.toLong())
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al eliminar habilidad: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
