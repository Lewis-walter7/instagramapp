package com.licoding.instagramapp.data.models

object HttpRoutes {
    private const val BASE_URL = "https://bfcf-105-163-0-47.ngrok-free.app"

    const val registerRoute = "$BASE_URL/register"

    const val loginRoute = "$BASE_URL/login"
    const val AUTHENTICATEROUTE = "$BASE_URL/authenticate"
    const val USERINFOROUTE = "$BASE_URL/user"
    const val UPLOAD = "$BASE_URL/upload"
}