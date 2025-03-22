package com.unir.character.data.model.remote

import com.unir.character.data.model.local.CharacterSkillCrossRef

data class CharacterRequest(
    val id: Long,
    val updatedAt: Long,


    val name: String,
    val description: String,
    val race: String,
    val level: Int,
    val armor: Int,
    val age: Int,
    val gold: Int,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,

    val hp: Int,
    val currentHp: Int,
    val ap: Int,
    val currentAp: Int,


    val imgUrl: String?,
    val gameSessionId: Int?,
    val userId: Int?,
    val roleClass: String,
    var skills: List<CharacterSkillCrossRef>

)
