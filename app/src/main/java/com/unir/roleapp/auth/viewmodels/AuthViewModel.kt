package com.roleapp.auth.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roleapp.auth.data.model.User
import com.roleapp.auth.domain.usecase.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: MANEJAR CORRECTAMENTE LOS MENSAJES DE ERROR EN EL SCREEN CORRESPONDIENTE

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCases,
) : ViewModel() {

    // Estado de la autenticación: LoggedOut, IDLE, LOADING, SUCCESS, ERROR
    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    val userState: StateFlow<UserState> = _userState

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage



    // Lanzar el autologin al inicializar el ViewModel
    init {
        if (_userState.value != UserState.LoggedOut) {
            autologin()
        }
    }

    fun login(email: String, password: String) {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = authUseCase.postLogin(email, password)
            _userState.value = result.fold(
                onSuccess = { UserState.Success(it) },
                onFailure = { UserState.Error(it.message ?: "Error en login") }
            )
            if (userState.value is UserState.Error) {
                val errorMessage = (userState.value as UserState.Error).message
                _errorMessage.value = errorMessage
                println("Error en login: $errorMessage")
            }

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
                onFailure = {
                    UserState.Error(it.message ?: "Error registrando usuario")
                }
            )
            if (userState.value is UserState.Error) {
                val errorMessage = (userState.value as UserState.Error).message
                _errorMessage.value = errorMessage
                println("Error en signup: $errorMessage")
            }

            if (userState.value is UserState.Success) {
                login(email, password)
            }

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

    fun clearErrorMessage() {
        _errorMessage.value = null

    }

}