package com.unir.sheet.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.unir.sheet.data.remote.model.ApiSkill

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




