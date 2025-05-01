package com.unir.roleapp.character.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.unir.roleapp.character.data.model.remote.SkillDTO
import com.unir.roleapp.character.data.model.local.StatName

@Entity(tableName = "SkillTable")
data class Skill(
    @PrimaryKey(autoGenerate = true) val id:Int,
    var name: String = "",
    var description: String = "",
    var tag: StatName,
){
    fun toApiSkill(): SkillDTO {
        return SkillDTO(
            id = this.id,
            name = this.name,
            description = this.description,
            tag = StatName.getString(this.tag)
        )
    }


    // DEvuelve el string con el nombre del icono (asegurarse de que coincida con el de la carpeta DRawable)
    fun getIcon(): String {
        return when (this.id) {
            21 -> "weapon_bow"
            22 -> "weapon_dagger"
            23 -> "weapon_spear"
            24 -> "weapon_axe"
            25 -> "weapon_sword"
            else -> "weapon_sword"
        }
    }

}

//// NO ES NECESARIO, LAS SKILLS SE CARGAN DESDE LA API
//object SkillTags {
//    const val STRENGTH = "STR"
//    const val DEXTERITY = "DEX"
//    const val INTELLIGENCE = "INT"
//    const val CHARISMA = "CHA"
//    const val COMBAT = "COMBAT"
//}
//
//fun tagToString(tag: String): String {
//    return when (tag) {
//        SkillTags.STRENGTH -> "Fuerza"
//        SkillTags.DEXTERITY -> "Destreza"
//        SkillTags.INTELLIGENCE -> "Conocimiento"
//        SkillTags.CHARISMA -> "Sociales"
//        SkillTags.COMBAT -> "Combate"
//        else -> {""}
//    }
//}

