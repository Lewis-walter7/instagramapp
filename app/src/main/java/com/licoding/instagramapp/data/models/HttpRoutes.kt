package com.licoding.instagramapp.data.models

object HttpRoutes {
    private const val BASE_URL = "https://d8aa-105-163-1-39.ngrok-free.app"
    const val registerRoute = "$BASE_URL/register"

    const val loginRoute = "$BASE_URL/login"
    const val AUTHENTICATEROUTE = "$BASE_URL/authenticate"
    const val USERINFOROUTE = "$BASE_URL/user"
    const val UPLOAD = "$BASE_URL/upload"
    const val UPDATEUSER = "$BASE_URL/updateuser"
    const val GETPOSTBYUSER = "$BASE_URL/getuserposts"
    const val EXPLORE = "$BASE_URL/getsearchposts"
}