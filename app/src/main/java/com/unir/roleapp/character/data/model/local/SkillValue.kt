package com.roleapp.character.data.model.local

data class SkillValue(
    val skill: Skill,
    val value: Int
){
    fun toCrossRef(characterId: Long): CharacterSkillCrossRef {
        return CharacterSkillCrossRef(
            characterId = characterId,
            skillId = this.skill.id,
            value = this.value
        )
    }
}
