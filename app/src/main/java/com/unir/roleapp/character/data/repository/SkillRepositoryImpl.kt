package com.roleapp.character.data.repository

import com.roleapp.character.data.dao.SkillDao
import com.roleapp.character.data.model.local.CharacterSkillCrossRef
import com.roleapp.character.data.model.local.Skill
import com.roleapp.character.data.model.local.SkillValue
import com.roleapp.character.data.model.remote.toSkill
import com.roleapp.character.data.service.SkillApiService
import com.roleapp.character.domain.repository.SkillRepository
import javax.inject.Inject


class SkillRepositoryImpl @Inject constructor(
    private val apiService: SkillApiService,
    private val skillDao: SkillDao
) : SkillRepository {

    override suspend fun getSkills(): Result<List<Skill>> {
        return try {

            val skills : List<Skill>  = skillDao.getSkills()
            Result.success(skills)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun fetchSkillsFromApi(): Result<List<Skill>> {
        return try {
            val response = apiService.getAllSkills()
            if (response.isSuccessful) {
                val skills = response.body()?.map { it.toSkill() } ?: emptyList()
                skillDao.insertOrUpdateSkills(skills)
                Result.success(skills)
            } else {
                Result.failure(Exception("Error al obtener habilidades: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }




    // MÉTODO PARA OBTENERLAS DE LA API
     override suspend fun fetchCharacterSkillsFromApi(characterId: Long): Result<List<Skill>> {
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

    override suspend fun getSkillsFromCharacter(characterId: Long): Result<List<SkillValue>> {
        return try {
            val skillsWithValues = skillDao.getSkillsWithValues(characterId)
            Result.success(skillsWithValues)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun generateSkills(
        skillsCrossRef : List<CharacterSkillCrossRef>,
        characterId: Long
    ) : Result<List<SkillValue>>  {
        return try {
            skillDao.insertCharacterSkills(skillsCrossRef)
            val skillsWithValues = skillDao.getSkillsWithValues(characterId)
            Result.success(skillsWithValues)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveSkills(characterId: Long, skills: List<SkillValue>): Result<Unit> {
        return try {
            skillDao.insertSkills(characterId, skills)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
