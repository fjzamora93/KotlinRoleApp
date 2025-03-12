package com.unir.sheet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.sheet.data.model.User
import com.unir.sheet.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    val userState: StateFlow<UserState> = _userState



    fun login(email: String, password: String) {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = repository.login(email, password)
            _userState.value = result.fold(
                onSuccess = { UserState.Success(it) },
                onFailure = { UserState.Error(it.message ?: "Error en login") }
            )
        }
    }

    fun signup(email: String, password: String, confirmPassword: String) {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = repository.signup(email, password, confirmPassword)
            _userState.value = result.fold(
                onSuccess = { UserState.Success(it) },
                onFailure = { UserState.Error(it.message ?: "Error actualizando usuario") }
            )
        }
    }

    fun logout() {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = repository.logout()
            _userState.value = result.fold(
                onSuccess = { UserState.LoggedOut },
                onFailure = { UserState.Error(it.message ?: "Error en logout") }
            )
        }
    }

    fun getUser() {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = repository.getUser()
            _userState.value = result.fold(
                onSuccess = { UserState.Success(it) },
                onFailure = { UserState.Error(it.message ?: "Error obteniendo usuario") }
            )
        }
    }



    fun updateUser(user: User) {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = repository.updateUser(user.toUserApi())
            _userState.value = result.fold(
                onSuccess = { UserState.Success(it) },
                onFailure = { UserState.Error(it.message ?: "Error actualizando usuario") }
            )
        }
    }

    fun deleteUser() {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = repository.deleteUser()
            _userState.value = result.fold(
                onSuccess = { UserState.Deleted },
                onFailure = { UserState.Error(it.message ?: "Error eliminando usuario") }
            )
        }
    }
}
