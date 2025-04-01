package com.roleapp.auth.domain.usecase.user

import com.roleapp.auth.data.model.User
import com.roleapp.auth.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<User> {
        return userRepository.getUser()
    }
}
