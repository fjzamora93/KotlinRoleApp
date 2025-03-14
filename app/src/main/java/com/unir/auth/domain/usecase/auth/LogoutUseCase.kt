package com.unir.auth.domain.usecase.auth

import com.unir.auth.data.model.User
import com.unir.auth.domain.repository.AuthRepository
import javax.inject.Inject

// TODO: REALIZAR VALIDACIONES PARA EL LOGOUT (SI ES QUE PROCEDE)
class PostLogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
)  {
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.logout()
    }
}