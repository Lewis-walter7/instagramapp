package com.licoding.instagramapp.presentation.upload

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.licoding.instagramapp.data.models.Image
import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.data.remote.post.UploadFile
import com.licoding.instagramapp.data.remote.repository.post.PostRepository
import com.licoding.instagramapp.domain.requests.PostRequest
import com.licoding.instagramapp.domain.room.AppDatabaseSingleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UploadViewModel(
    private val application: Application,
    private val postRepository: PostRepository
): ViewModel() {
    private var _state = MutableStateFlow(UploadUIState())
    val state = _state.asStateFlow()

    private val appDatabase = AppDatabaseSingleton.getDatabase(application)
    var user: User? = null

    init {
        viewModelScope.launch {
            user = appDatabase.userDao.getCurrentUser()
        }
    }
    var images by mutableStateOf(emptyList<Image>())
        private set
    fun updateImages(images: List<Image>) {
        this.images = images
    }

    fun onEvent(event: UploadUIEvent) {
        when(event) {
            is UploadUIEvent.OnSelectedUriChange -> {
                _state.update {
                    it.copy(
                        selectedUri = event.uri
                    )
                }
            }
            is UploadUIEvent.OnSelectedUrisChange -> {
                _state.update {
                    it.copy(
                        selectedUris = event.uris
                    )
                }
            }

            is UploadUIEvent.ShowAppBar -> {
                _state.update {
                    it.copy(
                        showAppBar = event.isToShow
                    )
                }
            }

            is UploadUIEvent.OnUploadButtonClicked -> {
                viewModelScope.launch {
                    val url = UploadFile(state.value.selectedUri, application)
                    val newPost = user?.let {
                        PostRequest(
                            postUrl = url.toString(),
                            userId = it.id,
                            showComments = state.value.turnOffComments,
                            hideLikes = state.value.hideLikes,
                            caption = state.value.caption
                        )
                    }

                    val response = newPost?.let {
                        postRepository.createPost(it)
                    }
                    println(response)
                }
            }

            is UploadUIEvent.OnCaptionChange -> {
                _state.update {
                    it.copy(
                        caption = event.caption
                    )
                }
            }

            is UploadUIEvent.OnHideLikesChange -> {
                _state.update {
                    it.copy(
                        hideLikes = event.bool
                    )
                }
            }
            is UploadUIEvent.OnAllowCommentsChange -> {
                _state.update {
                    it.copy(
                        turnOffComments = event.bool
                    )
                }
            }
        }
    }
}