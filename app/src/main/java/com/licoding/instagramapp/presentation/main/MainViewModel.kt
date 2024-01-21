package com.licoding.instagramapp.presentation.main

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.data.models.UserResult
import com.licoding.instagramapp.data.remote.dto.PostResponse
import com.licoding.instagramapp.data.remote.post.UploadFile
import com.licoding.instagramapp.data.remote.dto.UserResponse
import com.licoding.instagramapp.data.remote.post.PostApiServiceIpml
import com.licoding.instagramapp.data.remote.repository.user.GetCurrentUser
import com.licoding.instagramapp.data.remote.repository.user.UserRepository
import com.licoding.instagramapp.data.remote.user.UserApiServiceImpl
import com.licoding.instagramapp.domain.requests.EditedUserRequest
import com.licoding.instagramapp.domain.requests.FollowRequests
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
    var searchUserPosts: List<PostResponse> = emptyList()
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
            is MainUIEvent.OnSearchUsernameChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.update {
                        it.copy(
                            searchUsername = event.username
                        )
                    }
                    delay(500)
                    getSearchUsers(state.value.searchUsername!!)
                }
            }
            MainUIEvent.OnDeleteUserResult -> {
                println("Hey")
            }
            is MainUIEvent.OnUserResultClick -> {
                getUserById(event.id)
                viewModelScope.launch {
                    searchUserPosts = userRepository.getSearchedUserPosts(event.id)
                }
                //appDatabase.resultRecentSearchDao.upsertResult()
            }
            MainUIEvent.OnFollowButtonClicked -> {
                val followRequests = FollowRequests(
                    followerId = state.value.currentUser?.id!!,
                    followingId = state.value.searchedUser?.id!!
                )
                viewModelScope.launch {
                    UserApiServiceImpl.followUser(followRequests)
                    GetUserInfo()
                    delay(50)
                    getUserById(followRequests.followingId)
                }
            }
        }
    }

    fun updateImages(){
        viewModelScope.launch(Dispatchers.IO) {
            posts = postApiService.getPostByUser()
            searchPosts = postApiService.getExplorePosts().shuffled()
        }
    }


    fun getSearchUsers(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            val users = userRepository.getUsersByUsername(username)
            _state.update {
                it.copy(
                    searchUsers = users.toUserResult()
                )
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
            accountType = accountType,
            followingCount = followingCount,
            followerCount = followerCount,
            postCount = postCount
        )
    }

    private fun getUserById(id: String) {
        viewModelScope.launch {
            val user = userRepository.getUserById(id)
            _state.update {
                it.copy(
                    searchedUser = user.toUser()
                )
            }
            val userResult = UserResult(
                id = user.id,
                username = user.username,
                name = user.name,
                profileImage = user.profileImage
            )
            appDatabase.resultRecentSearchDao.upsertResult(userResult)
        }
    }
    private suspend fun GetUserInfo() {
        val response = userRepository.getUserInfo()
        if (response != null) {
            appDatabase.userDao.upsertUser(response.toUser())
        } else return
    }

}

private fun List<UserResponse>.toUserResult(): List<UserResult> {
    return this.map { response ->
        UserResult(
            id = response.id,
            username = response.username,
            name = response.name,
            profileImage = response.profileImage
        )
    }
}

