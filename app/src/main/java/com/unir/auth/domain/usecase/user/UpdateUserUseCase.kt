package com.unir.auth.domain.usecase.user

import com.unir.auth.data.model.User
import com.unir.auth.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): Result<User> {
        return userRepository.updateUser(user)
    }
}
