package com.roleapp.auth.domain.usecase.user

import com.roleapp.auth.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return userRepository.deleteUser()
    }
}
