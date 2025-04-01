package com.roleapp.auth.domain.usecase.auth


/**
 * Este data class contiene todos los UseCases que se encargan de la autenticación.
 * El objetivo es crear una instancia única de los UseCases y que sea accesible desde el ViewModel.
 *
 * Es necesario añadir este dataclass a la inyección de dependencias del módulo.
 */
data class AuthUseCases(
    val postLogin: PostLoginUseCase,
    val postSignup: PostSignupUseCase,
    val postLogout: PostLogoutUseCase,
    val postAutologin: PostAutologinUseCase
)
