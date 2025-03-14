package com.unir.auth.domain.usecase.auth

import com.unir.auth.data.model.User
import com.unir.auth.domain.repository.AuthRepository
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String):
            Result<User> {
        return authRepository.login(email, password)
    }
}