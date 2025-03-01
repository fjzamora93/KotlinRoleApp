package com.unir.sheet.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.unir.sheet.data.remote.model.ApiUser

@Entity( tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    val name: String,
    val email: String,
) {

    fun toUserApi(): ApiUser {
        return ApiUser(
            id = this.id,
            name = this.name,
            email = this.email,
        )
        }
}
