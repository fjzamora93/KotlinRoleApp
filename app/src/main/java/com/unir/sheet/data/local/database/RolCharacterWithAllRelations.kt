package com.unir.sheet.data.local.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.unir.sheet.data.model.CharacterItemCrossRef
import com.unir.sheet.data.model.CharacterSkillCrossRef
import com.unir.sheet.data.model.CharacterSpellCrossRef
import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.RolCharacter
import com.unir.sheet.data.model.Skill
import com.unir.sheet.data.model.Spell

data class RolCharacterWithAllRelations(
    @Embedded val rolCharacter: RolCharacter,

    // RELACIÓN DE MUCHOS A MUCHOS
    @Relation(
        parentColumn = "id", // id en la tabla RolCharacter
        entityColumn = "id", // id de la tabla CharacterItemCrossRef que referencia a Item
        associateBy = Junction(
            CharacterItemCrossRef::class,
            parentColumn = "characterId", // id en CharacterItemCrossRef que referencia a RolCharacter
            entityColumn = "itemId" // id en CharacterItemCrossRef que referencia a Item
        )
    )
    val items: List<Item>,


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

