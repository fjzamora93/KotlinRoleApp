package com.roleapp.auth.viewmodels

import com.roleapp.auth.data.model.User

sealed class UserState {
    object Idle : UserState()
    object Loading : UserState()
    data class Success(val user: User) : UserState()
    data class Error(val message: String) : UserState()
    object LoggedOut : UserState()
    object Deleted : UserState()
}
