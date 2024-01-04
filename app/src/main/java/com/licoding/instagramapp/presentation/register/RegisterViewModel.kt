package com.licoding.instagramapp.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    //handle splash screen show up
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()


    init {
        viewModelScope.launch {
            delay(3000)
            _isLoading.value = false
        }
    }

    private val _state = MutableStateFlow(RegisterUIState())
    val state = _state.asStateFlow()

    fun onEvent(event: RegisterUIEvent) {
        when(event){
            is RegisterUIEvent.OnLoginButtonClicked -> {
                //login user, communicate with the database
            }
            is RegisterUIEvent.OnRegisterButtonClicked -> {
                // register user
            }
            is RegisterUIEvent.PasswordChange -> {
                //check the length and strength
            }
            is RegisterUIEvent.UserNameChange -> {
                viewModelScope.launch {
                    delay(500)
                }
            }
        }

    }

}