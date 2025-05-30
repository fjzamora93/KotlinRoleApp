package com.roleapp.auth.data.model


data class UserDTO(
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
