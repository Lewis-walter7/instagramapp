package com.licoding.instagramapp.presentation.main

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.data.remote.dto.PostResponse
import com.licoding.instagramapp.data.remote.post.UploadFile
import com.licoding.instagramapp.data.remote.dto.UserResponse
import com.licoding.instagramapp.data.remote.post.PostApiServiceIpml
import com.licoding.instagramapp.data.repository.user.GetCurrentUser
import com.licoding.instagramapp.data.repository.user.UserRepository
import com.licoding.instagramapp.domain.requests.EditedUserRequest
import com.licoding.instagramapp.domain.room.AppDatabaseSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val application: Application,
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences,
) : ViewModel(){
    private val appDatabase = AppDatabaseSingleton.getDatabase(application)
    private val postApiService = PostApiServiceIpml(sharedPreferences)

    private val _state = MutableStateFlow(MainUIState())
    val state = _state.asStateFlow()

    var posts: List<PostResponse> = emptyList()
    var searchPosts: List<PostResponse> = emptyList()
    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    currentUser = GetCurrentUser(appDatabase.userDao)
                )
            }
        }
        updateImages()
    }
    fun onEvent(event: MainUIEvent) {
        when(event) {
            is MainUIEvent.OnBioChange -> {
                _state.update {
                    it.copy(
                        bio = event.bio
                    )
                }
            }
            is MainUIEvent.OnNameChange -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
            is MainUIEvent.OnUsernameChange -> {
                _state.update {
                    it.copy(
                        requestedUsername = event.username,
                    )
                }
            }

            is MainUIEvent.OnSelectedUriChange -> {
                _state.update {
                    it.copy(
                        selectedUri = event.selecteduri
                    )
                }
            }

            MainUIEvent.OnUpdateButtonClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (state.value.selectedUri != null) {
                        val url = UploadFile(state.value.selectedUri, application)
                        val userRequest = state.value.currentUser?.let {
                            EditedUserRequest(
                                name = state.value.name,
                                bio = state.value.bio,
                                username = it.username,
                                profileImage = url.toString(),
                                id = it.id
                            )
                        }
                        userRequest?.let {
                            userRepository.updateUser(it)
                        }
                        val userReponse = userRepository.getUserInfo()
                        if (userReponse != null) {
                            appDatabase.userDao.upsertUser(userReponse.toUser())
                        }
                        _state.update {
                            it.copy(
                                currentUser = GetCurrentUser(appDatabase.userDao)
                            )
                        }
                    } else  if (state.value.selectedUri != null && state.value.requestedUsername != null) {
                        val url = UploadFile(state.value.selectedUri, application)
                        val userRequest = state.value.currentUser?.let {
                            EditedUserRequest(
                                name = state.value.name,
                                bio = state.value.bio,
                                username = it.username,
                                profileImage = url.toString(),
                                requestedUsername = state.value.requestedUsername,
                                id = it.id
                            )
                        }
                        userRequest?.let {
                            userRepository.updateUser(it)
                        }
                        val userReponse = userRepository.getUserInfo()
                        if (userReponse != null) {
                            appDatabase.userDao.upsertUser(userReponse.toUser())
                        }
                        _state.update {
                            it.copy(
                                currentUser = GetCurrentUser(appDatabase.userDao)
                            )
                        }
                    } else if(state.value.requestedUsername != null){
                        val userRequest = state.value.currentUser?.let {
                            EditedUserRequest(
                                name = state.value.name,
                                bio = state.value.bio,
                                username = it.username,
                                requestedUsername = state.value.requestedUsername,
                                id = it.id
                            )
                        }
                        userRequest?.let {
                            val response =  userRepository.updateUserWithUsername(it)
                            sharedPreferences.edit()
                                .putString("jwt-token", response.token)
                                .apply()
                        }
                        delay(1000)
                        val userReponse = userRepository.getUserInfo()
                        if (userReponse != null) {
                            appDatabase.userDao.upsertUser(userReponse.toUser())
                        }
                        _state.update {
                            it.copy(
                                currentUser = GetCurrentUser(appDatabase.userDao)
                            )
                        }
                    } else {
                        val userRequest = state.value.currentUser?.let {
                            EditedUserRequest(
                                name = state.value.name,
                                bio = state.value.bio,
                                username = it.username,
                                id = it.id
                            )
                        }
                        userRequest?.let {
                            userRepository.updateUser(it)
                        }
                        val userReponse = userRepository.getUserInfo()
                        if (userReponse != null) {
                            appDatabase.userDao.upsertUser(userReponse.toUser())
                        }
                        _state.update {
                            it.copy(
                                currentUser = GetCurrentUser(appDatabase.userDao)
                            )
                        }
                    }
                }
            }

            is MainUIEvent.OnShowBottomBarChange -> {
                _state.update {
                    it.copy(
                        showBottomBar = event.value
                    )
                }
            }
        }
    }

    private fun UserResponse.toUser(): User {
        return User(
            id = id,
            username = username,
            password = password,
            createdAt = createdAt,
            name = name,
            profileImage = profileImage,
            bio = bio,
            phoneNumber = phoneNumber,
            email = email,
            accountType = accountType
        )
    }

    fun updateImages(){
        viewModelScope.launch(Dispatchers.IO) {
            posts = postApiService.getPostByUser()
            searchPosts = postApiService.getExplorePosts().shuffled()
        }
    }
}