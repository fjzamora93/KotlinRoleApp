package com.unir.sheet.data.remote.model

import com.unir.sheet.data.model.Skill
import com.unir.sheet.data.model.Spell
import com.unir.sheet.data.model.User


data class ApiUser(
    val id: Int? = null,
    val name: String,
    val email: String,
){
    fun toUserEntity(): User {
        return User(
            id = this.id,
            name = this.name,
            email = this.email,
        )
    }
}
