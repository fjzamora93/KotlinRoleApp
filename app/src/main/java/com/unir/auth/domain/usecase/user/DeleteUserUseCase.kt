package com.unir.auth.domain.usecase.user

import com.unir.auth.data.model.User
import com.unir.auth.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return userRepository.deleteUser()
    }
}
