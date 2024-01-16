package com.licoding.instagramapp.data.remote

import kotlinx.coroutines.flow.Flow

interface NetworkObserver {
    fun observerNetworkStatus(): Flow<NetworkStatus>

    enum class NetworkStatus {
        Available, UnAvailable, Lost, Loosing
    }
}