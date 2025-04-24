package com.roleapp.character.data.repository

import android.util.Log
import com.roleapp.character.data.dao.SkillDao
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.CharacterSkillCrossRef
import com.roleapp.character.data.model.local.Skill
import com.roleapp.character.data.model.local.SkillValue
import com.roleapp.character.data.model.remote.toSkill
import com.roleapp.character.data.service.CharacterApiService
import com.roleapp.character.data.service.SkillApiService
import com.roleapp.character.domain.repository.SkillRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SkillRepositoryImpl @Inject constructor(
    private val apiService: SkillApiService,
    private val characterApi: CharacterApiService,
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
     override suspend fun fetchSkillsFromApi(characterId: Long): Result<List<Skill>> {
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
            skillDao.upsertCharacterSkills(skillsCrossRef)
            val skillsWithValues = skillDao.getSkillsWithValues(characterId)
            Result.success(skillsWithValues)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Inserta las habilidades y cambia la fecha de actualizacion del personaje
    override suspend fun saveSkills(character: CharacterEntity, skills: List<SkillValue>): Result<Unit> {
        return try {
            skillDao.insertSkills(character, skills)

            val skillsCrossRef: List<CharacterSkillCrossRef> = skillDao.getCharacterSkills(character.id)

            // 2. Intentar guardar en API usando tu función existente
            upsertCharacterToApi(character, skillsCrossRef)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchCharacterSkillsFromApi(characterId: Long): Result<List<SkillValue>> {
        return try {
            val characterResponse = characterApi.getCharacterById(characterId)

            if (!characterResponse.isSuccessful) {
                return Result.failure(Exception("Error HTTP ${characterResponse.code()}"))
            }

            val apiCharacter = characterResponse.body()
                ?: return Result.failure(Exception("Datos del personaje vacíos"))


            // 2. Insertar nuevas habilidades
            val crossRefSkills = apiCharacter.skills.map { skillDTO ->
                CharacterSkillCrossRef(
                    characterId = characterId,
                    skillId = skillDTO.skill.id,
                    value = skillDTO.value
                )
            }
            skillDao.upsertCharacterSkills(crossRefSkills)

            // 3. Obtener y retornar las habilidades actualizadas
            val skillsWithValues = skillDao.getSkillsWithValues(characterId)
            Result.success(skillsWithValues)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun upsertCharacterToApi(
        character: CharacterEntity,
        skillsCrossRef: List<CharacterSkillCrossRef>
    ): Result<CharacterEntity> = withContext(Dispatchers.IO) {
        try {
            val characterRequest = character.toApiRequest().apply {
                this.characterSkills = skillsCrossRef
            }

            val response = characterApi.saveCharacter(characterRequest)

            if (!response.isSuccessful || response.body() == null) {
                Log.e("API Error", "Fallo al guardar en la API")
                return@withContext Result.failure(Exception("No ha sido posible guardar al personaje en la API"))
            }

            Result.success(response.body()!!.toCharacterEntity())
        } catch (e: Exception) {
            Log.e("API Error", "Fallo al guardar en la API", e)
            Result.failure(e)
        }
    }





}
