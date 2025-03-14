package com.unir.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.auth.data.model.User
import com.unir.auth.data.repository.AuthRepositoryImpl
import com.unir.auth.data.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: ACTUALMENTE SIN NINGUNA PANTALLA DISPONIBLE
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    val userState: StateFlow<UserState> = _userState


    fun getUser() {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = userRepository.getUser()
            _userState.value = result.fold(
                onSuccess = { UserState.Success(it) },
                onFailure = { UserState.Error(it.message ?: "Error obteniendo usuario") }
            )
        }
    }



    fun updateUser(user: User) {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = userRepository.updateUser(user.toUserApi())
            _userState.value = result.fold(
                onSuccess = { UserState.Success(it) },
                onFailure = { UserState.Error(it.message ?: "Error actualizando usuario") }
            )
        }
    }

    fun deleteUser() {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = userRepository.deleteUser()
            _userState.value = result.fold(
                onSuccess = { UserState.Deleted },
                onFailure = { UserState.Error(it.message ?: "Error eliminando usuario") }
            )
        }
    }
}