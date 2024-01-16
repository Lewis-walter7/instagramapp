package com.licoding.instagramapp.data.models

object HttpRoutes {
    private const val BASE_URL = "https://09ad-105-163-2-86.ngrok-free.app"

    const val registerRoute = "$BASE_URL/register"

    const val loginRoute = "$BASE_URL/login"
    const val AUTHENTICATEROUTE = "$BASE_URL/authenticate"
    const val USERINFOROUTE = "$BASE_URL/user"
}