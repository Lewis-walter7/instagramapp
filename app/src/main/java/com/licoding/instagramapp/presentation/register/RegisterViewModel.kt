package com.licoding.instagramapp.presentation.register

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.data.remote.NetworkObserver
import com.licoding.instagramapp.data.remote.dto.UserResponse
import com.licoding.instagramapp.data.remote.repository.user.UserRepository
import com.licoding.instagramapp.domain.requests.UserRequest
import com.licoding.instagramapp.domain.room.UserDao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences,
    private val userDao: UserDao,
    connectivityManager: NetworkObserver
): ViewModel() {
     var user: User? = null

    init {
        viewModelScope.launch {
            Authenticate()
            delay(5000)
            _isLoading.value = false

            user = userDao.getCurrentUser()
            GetUserInfo()
            println(networkStatus)
        }
    }

   private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _state = MutableStateFlow(RegisterUIState())
    val state = _state.asStateFlow()
    var password =""
    var username = ""

    private val responses = Channel<Boolean>()
    val responseChannel = responses.receiveAsFlow()

    val newUser = UserRequest(
        username = username,
        password = password
    )
    val networkStatus = connectivityManager.observerNetworkStatus().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(3000), NetworkObserver.NetworkStatus.UnAvailable
    )
    @SuppressLint("CommitPrefEdits")
    fun onEvent(event: RegisterUIEvent) {
        when(event){
            is RegisterUIEvent.OnLoginButtonClicked -> {
                val userRequest = event.userRequest
                viewModelScope.launch {
                    val response = userRepository.loginUser(userRequest)
                    sharedPreferences.edit()
                        .putString("jwt-token", response.token)
                        .apply()
                    Authenticate()
                    GetUserInfo()
                }
                password = ""
                username = ""
            }
            is RegisterUIEvent.OnRegisterButtonClicked -> {
                val username1 = sharedPreferences.getString("username", null)
                val newUserRequest = UserRequest(
                    username = username1!!,
                    password = password
                )
                viewModelScope.launch {
                    newUserRequest.let {
                        userRepository.createUser(it)
                    }
                }
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

    private suspend fun Authenticate() {
        viewModelScope.launch {
            val response = userRepository.authenticate()
            responses.send(response.isAuthenticated)
            if (!response.isAuthenticated) {
                userDao.deleteUserByUserId()
            }
        }
    }
    private suspend fun GetUserInfo() {
        val response = userRepository.getUserInfo()
        if (response != null) {
            userDao.upsertUser(response.toUser())
        } else return
    }

    private fun UserResponse.toUser(): User {
        return User(
            id = id,
            username = username,
            password = password,
            createdAt = createdAt,
            profileImage = profileImage,
            bio = bio,
            phoneNumber = phoneNumber,
            email = email,
            accountType = accountType,
            name = name,
            followerCount = followerCount,
            followingCount = followingCount,
            postCount = postCount
        )
    }
}