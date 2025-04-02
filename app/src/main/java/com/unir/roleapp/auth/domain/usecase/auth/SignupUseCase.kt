package com.roleapp.auth.domain.usecase.auth

import com.roleapp.auth.data.model.User
import com.roleapp.auth.domain.repository.AuthRepository
import javax.inject.Inject


// TODO: VALIDACIONES DE LA CONTRASEÑA (LONGITUD, QUE COINCIDEN, ETC...)
class PostSignupUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String
    ): Result<User> {
        if (password != confirmPassword) {
            return Result.failure(Exception("Las contraseñas no coinciden"))
        }

        if (password.length < 3) {
            return Result.failure(Exception("La contraseña debe tener al menos 4 caracteres"))
        }

        if (!isValidEmail(email)) {
            return Result.failure(Exception("El email no tiene un formato válido"))
        }

        return authRepository.signup(email, password)
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }
}