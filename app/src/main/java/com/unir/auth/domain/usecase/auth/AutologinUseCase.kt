package com.unir.auth.domain.usecase.auth

import com.unir.auth.data.model.User
import com.unir.auth.domain.repository.AuthRepository
import com.unir.character.data.model.local.Spell
import javax.inject.Inject

// TODO: MANEJAR ALGUNA LÓGICA ADICIONAL DE AUTOLOGIN (La verificación del refresh token se hace en el repository, NUNCA en el UseCase
class PostAutologinUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<User> {
        return authRepository.autoLogin()
    }
}