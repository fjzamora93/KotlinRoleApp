package com.unir.character.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.unir.character.data.model.remote.ApiSkill

@Entity(tableName = "SkillTable")
data class Skill(
    @PrimaryKey(autoGenerate = true) val id:Int,
    var name: String = "",
    var description: String = "",
){
    fun toApiSkill(): ApiSkill {
        return ApiSkill(
            id = this.id,
            name = this.name,
            description = this.description)
    }
}




