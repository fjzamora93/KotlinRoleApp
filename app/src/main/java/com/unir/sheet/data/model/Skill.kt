package com.unir.sheet.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "SkillTable")
data class Skill(
    @PrimaryKey(autoGenerate = true) val id:Int,
    var name: String = "",
    var description: String = "",
)


