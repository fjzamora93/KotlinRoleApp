package com.unir.auth.domain.usecase.user

data class UserUseCase(
    val getUser: GetUserUseCase,
    val updateUser: UpdateUserUseCase,
    val deleteUser: DeleteUserUseCase
)
