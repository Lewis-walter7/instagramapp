package com.licoding.instagramapp.presentation.register

import com.licoding.instagramapp.domain.requests.UserRequest

sealed interface RegisterUIEvent {
    data class OnLoginButtonClicked(val userRequest: UserRequest): RegisterUIEvent
    object OnRegisterButtonClicked: RegisterUIEvent
    object ClearFields: RegisterUIEvent
    data class OnLoginUserNameChange(val username: String): RegisterUIEvent
    data class OnLoginPasswordChange(val password: String): RegisterUIEvent
    data class OnRegisterUserNameChange(val username: String): RegisterUIEvent
    data class OnRegisterPasswordChange(val password: String): RegisterUIEvent
}