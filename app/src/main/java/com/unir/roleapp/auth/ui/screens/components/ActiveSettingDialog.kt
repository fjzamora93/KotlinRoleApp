package com.unir.roleapp.auth.ui.screens.components

sealed class ActiveSettingDialog {
    object None : ActiveSettingDialog()
    object User : ActiveSettingDialog()
    object Language : ActiveSettingDialog()
    object Develop : ActiveSettingDialog()
    object Privacy : ActiveSettingDialog()
}
