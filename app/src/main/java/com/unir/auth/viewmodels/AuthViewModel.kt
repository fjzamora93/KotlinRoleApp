package com.unir.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.auth.domain.repository.AuthRepository
import com.unir.auth.domain.usecase.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCases,
) : ViewModel() {

    // Estado de la autenticación: IDLE, LOADING, SUCCESS, ERROR
    // TODO: MANEJAR CORRECTAMENTE LOS MENSAJES DE ERROR EN EL SCREEN CORRESPONDIENTE
    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    val userState: StateFlow<UserState> = _userState


    // Lanzar el autologin al inicializar el ViewModel
    // TODO: Si falla, hacer que aparezca un Toast para que se haga el login manualmente
    init {
        autologin()
    }

    fun login(email: String, password: String) {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = authUseCase.postLogin(email, password)
            _userState.value = result.fold(
                onSuccess = { UserState.Success(it) },
                onFailure = { UserState.Error(it.message ?: "Error en login") }
            )
        }
    }

    fun autologin() {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = authUseCase.postAutologin()
            _userState.value = result.fold(
                onSuccess = { UserState.Success(it) },
                onFailure = { UserState.Error(it.message ?: "Error en autologin") }
            )
        }
    }

    fun signup(email: String, password: String, confirmPassword: String) {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = authUseCase.postSignup(email, password, confirmPassword)
            _userState.value = result.fold(
                onSuccess = { UserState.Success(it) },
                onFailure = { UserState.Error(it.message ?: "Error actualizando usuario") }
            )
        }
    }

    fun logout() {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = authUseCase.postLogout()
            _userState.value = result.fold(
                onSuccess = { UserState.LoggedOut },
                onFailure = { UserState.Error(it.message ?: "Error en logout") }
            )
        }
    }

}
