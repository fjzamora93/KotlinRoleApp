package com.unir.sheet.data.remote.model

import com.unir.sheet.data.model.Skill
import com.unir.sheet.data.model.User


data class ApiUser(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,

)
fun ApiUser.toUserEntity(): User {
    return User(
        id = this.id,
        name = this.username,
        email = this.email,
        password = this.password
    )
}
