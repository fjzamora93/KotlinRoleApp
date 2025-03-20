package com.unir.character.data.model.remote

import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.Race
import com.unir.character.data.model.local.RolClass

data class CharacterResponse(
    val id: Long,
    val userId: Int,
    val updatedAt: Long,

    val name: String,
    val description: String,
    val race: String,
    val armor: Int,
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
    val roleClass: RoleClassDTO?,
    val items: List<ItemDTO>,
    val skills: List<CharacterSkillResponseDTO>
) {
    fun toCharacterEntity(): CharacterEntity {
        return CharacterEntity(
            id = this.id,
            userId = this.userId,
            gameSessionId = this.gameSessionId,
            updatedAt = this.updatedAt,

            name = this.name,
            description = this.description,
            rolClass = RolClass.valueOf(this.roleClass?.name ?: "WARRIOR"),
            race = Race.valueOf(this.race),
            armor = this.armor,
            age = this.age,
            gold = this.gold,
            strength = this.strength,
            dexterity = this.dexterity,
            constitution = this.constitution,
            intelligence = this.intelligence,
            wisdom = this.wisdom,
            charisma = this.charisma,
            hp = (this.constitution + this.armor) / 2,
            currentHp = this.constitution * 2,
            ap = (this.intelligence + this.wisdom) / 2,
            currentAp = this.intelligence / 2,
            level = 1
        )
    }


}
