package com.licoding.instagramapp.presentation.register

sealed class RegisterUIEvent {
    object OnLoginButtonClicked: RegisterUIEvent()
    object OnRegisterButtonClicked: RegisterUIEvent()
    object UserNameChange: RegisterUIEvent()
    object PasswordChange: RegisterUIEvent()
}