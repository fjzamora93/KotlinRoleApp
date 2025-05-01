package com.unir.roleapp.auth.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    val name: String,
    val email: String,
) {

    fun toUserApi(): UserDTO {
        return UserDTO(
            id = this.id,
            name = this.name,
            email = this.email,
        )
    }
}
