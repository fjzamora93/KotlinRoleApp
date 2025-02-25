package com.unir.sheet.ui.viewmodels

import com.unir.sheet.data.remote.model.ApiUser

sealed class UserState {
    object Idle : UserState()
    object Loading : UserState()
    data class Success(val user: ApiUser) : UserState()
    data class Error(val message: String) : UserState()
    object LoggedOut : UserState()
    object Deleted : UserState()
}
