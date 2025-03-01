package com.unir.sheet.data.remote.model

import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.Gender
import com.unir.sheet.data.model.Race
import com.unir.sheet.data.model.RolClass

data class ApiCharacterResponse(
    val id: Int,
    val userId: Int,

    val name: String,
    val description: String,
    val race: String,
    val gender: String,
    val size: Int,
    val age: Int,
    val gold: Int,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val imgUrl: String?,
    val gameSessionId: Int?,
    val roleClass: ApiRoleClass?,
    val items: List<ApiItem>,
    val skills: List<ApiSkill>
) {
    fun toCharacterEntity(): CharacterEntity {
        return CharacterEntity(
            id = this.id,
            userId = this.userId,
            gameSessionId = this.gameSessionId,
            name = this.name,
            description = this.description,
            rolClass = RolClass.valueOf(this.roleClass?.name ?: "WARRIOR"),
            gender = Gender.valueOf(this.gender),
            race = Race.valueOf(this.race),
            size = this.size,
            age = this.age,
            gold = this.gold,
            strength = this.strength,
            dexterity = this.dexterity,
            constitution = this.constitution,
            intelligence = this.intelligence,
            wisdom = this.wisdom,
            charisma = this.charisma,
            hp = (this.constitution + this.size) / 2,
            currentHp = this.constitution * 2,
            ap = (this.intelligence + this.wisdom) / 2,
            currentAp = this.intelligence / 2,
            level = 1
        )
    }


}
