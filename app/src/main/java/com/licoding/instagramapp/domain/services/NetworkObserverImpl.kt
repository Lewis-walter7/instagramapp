package com.licoding.instagramapp.domain.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.licoding.instagramapp.data.remote.NetworkObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NetworkObserverImpl(
    context: Context
): NetworkObserver {

    private val networkConnecctivity =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    override fun observerNetworkStatus(): Flow<NetworkObserver.NetworkStatus> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch {
                        send(NetworkObserver.NetworkStatus.Available)
                    }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch {
                        send(NetworkObserver.NetworkStatus.UnAvailable)
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch {
                        send(NetworkObserver.NetworkStatus.Lost)
                    }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch {
                        send(NetworkObserver.NetworkStatus.Loosing)
                    }
                }
            }
            networkConnecctivity.registerDefaultNetworkCallback(callback)
            awaitClose {
                networkConnecctivity.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}