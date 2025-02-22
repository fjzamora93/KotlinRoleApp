package com.unir.sheet.data.local.dao

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.unir.sheet.data.model.CharacterSkillCrossRef
import com.unir.sheet.data.model.CharacterSpellCrossRef
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.Skill
import com.unir.sheet.data.model.Spell

data class RolCharacterWithAllRelations(
    @Embedded val characterEntity: CharacterEntity,




    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            CharacterSpellCrossRef::class,
            parentColumn = "characterId",
            entityColumn = "spellId"
        )
    )
    val spells: List<Spell>,




    // RELACIÓN DE UNO A MUCHOS (PENDIENTE DE IMPLEMENTAR)
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            CharacterSkillCrossRef::class,
            parentColumn = "characterId",
            entityColumn = "skillId"
        )
    )
    val skills: List<Skill>,
)

