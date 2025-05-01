package com.unir.roleapp.character.data.model.remote

import com.unir.roleapp.character.data.model.local.CharacterEntity
import com.unir.roleapp.character.data.model.local.Race
import com.unir.roleapp.character.data.model.local.RolClass
import com.unir.roleapp.character.data.model.local.SkillValue

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

    val level: Int,
    val hp: Int,
    val currentHp: Int,
    val ap: Int,
    val currentAp: Int,


    val imgUrl: String,
    val gameSessionId: Int,
    val roleClass: RoleClassDTO?,
    val items: List<ItemDTO>,
    val skills: List<SkillValue>
) {
    fun toCharacterEntity(): CharacterEntity {
        return CharacterEntity(
            id = this.id,
            userId = this.userId,
            gameSessionId = this.gameSessionId,
            updatedAt = this.updatedAt,

            name = this.name,
            description = this.description,
            imgUrl = this.imgUrl,
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
            hp = this.hp,
            currentHp = this.currentHp,
            ap = this.ap,
            currentAp = this.currentAp,
            _level = level
        )
    }


}
