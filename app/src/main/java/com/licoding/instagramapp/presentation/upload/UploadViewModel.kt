package com.licoding.instagramapp.presentation.upload

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.licoding.instagramapp.data.models.Image
import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.domain.room.AppDatabaseSingleton
import com.licoding.instagramapp.domain.room.UserDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UploadViewModel(
    private val application: Application
): ViewModel() {
    private var _state = MutableStateFlow(UploadUIState())
    val state = _state.asStateFlow()

    private val appDatabase = AppDatabaseSingleton.getDatabase(application)
    var user: User? = null

    init {
        viewModelScope.launch {
            user = appDatabase.userDao.getCurrentUser()!!
        }
    }
    var images by mutableStateOf(emptyList<Image>())
        private set
    fun updateImages(images: List<Image>) {
        this.images = images
    }

    fun onEvent(event: UploadUIEvent) {
        when(event) {
            is UploadUIEvent.onSelectedUriChange -> {
                _state.update {
                    it.copy(
                        selectedUri = event.uri
                    )
                }
            }
            is UploadUIEvent.onSelectedUrisChange -> {
                _state.update {
                    it.copy(
                        selectedUris = event.uris
                    )
                }
            }
        }
    }
}