package com.licoding.instagramapp.presentation.register

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.licoding.instagramapp.data.repository.UserRepository
import com.licoding.instagramapp.domain.requests.UserRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

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
    var password =""
    var username = ""

    val newUser = UserRequest(
        username = username,
        password = password
    )
    @SuppressLint("CommitPrefEdits")
    fun onEvent(event: RegisterUIEvent) {
        when(event){
            is RegisterUIEvent.OnLoginButtonClicked -> {
                val userRequest = event.userRequest
                viewModelScope.launch {
                    val response = userRepository.loginUser(userRequest)
                    delay(500)
                    println(response)
                }
            }
            is RegisterUIEvent.OnRegisterButtonClicked -> {
                val username1 = sharedPreferences.getString("username", null)
                val newUserRequest = UserRequest(
                    username = username1!!,
                    password = password
                )
//                viewModelScope.launch {
//                    userRequest.let {
//                        userRepository.createUser(it)
//                    }
//                }
                println(newUserRequest)
            }
            is RegisterUIEvent.OnLoginPasswordChange -> {
                password = event.password
            }
            is RegisterUIEvent.OnLoginUserNameChange -> {
                username = event.username
                viewModelScope.launch {
                    delay(500)
                }
            }
            RegisterUIEvent.ClearFields -> {
                _state.update {
                    it.copy(
                        userNameError = null,
                        passwordError = null
                    )
                }
            }

            is RegisterUIEvent.OnRegisterUserNameChange -> {
                _state.update {
                    it.copy(
                        registerUsername = event.username
                    )
                }
            }
            is RegisterUIEvent.OnRegisterPasswordChange -> {
                password = event.password
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.edit().remove("username").apply()
    }
}