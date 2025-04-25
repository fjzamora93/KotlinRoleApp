package com.roleapp.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roleapp.auth.data.model.User
import com.roleapp.auth.data.repository.UserRepositoryImpl
import com.unir.roleapp.adventure.data.service.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _userEmail = MutableStateFlow(userPreferences.getEmail())
    val userEmail: StateFlow<String?> = _userEmail



    init {
        getUser()
    }

    fun getUser() {
        _loading.value = true
        viewModelScope.launch {
            val result = userRepository.getUser()
            result.onSuccess { _user.value = it }
                .onFailure { _errorMessage.value = it.message }
            _loading.value = false
        }
    }

    fun updateUser(user: User) {
        _loading.value = true
        viewModelScope.launch {
            val result = userRepository.updateUser(user)
            result.onSuccess { _user.value = it }
                .onFailure { _errorMessage.value = it.message }
            _loading.value = false
        }
    }

    fun deleteUser() {
        _loading.value = true
        viewModelScope.launch {
            val result = userRepository.deleteUser()
            result.onSuccess { _user.value = null }
                .onFailure { _errorMessage.value = it.message }
            _loading.value = false
        }
    }
}
