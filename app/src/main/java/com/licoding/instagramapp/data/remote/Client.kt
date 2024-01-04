package com.licoding.instagramapp.data.remote

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import kotlinx.serialization.json.Json

val Client= HttpClient(Android) {
    install(Logging) {
        level = LogLevel.ALL
    }
    install(ContentNegotiation) {
        Json {
            prettyPrint = true
            isLenient = true
        }
    }
}