package com.licoding.instagramapp.domain.requests

import kotlinx.serialization.Serializable

//both follow and unfollow requests
@Serializable
data class FollowRequests(
    val followerId: String,
    val followingId: String
)