package com.example.ktorexample.network

import android.util.Log
import com.example.ktorexample.model.Post
import io.ktor.client.*
import io.ktor.client.engine.android.*

import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import javax.inject.Inject

class ApiClass @Inject constructor() {

    var client = HttpClient(Android) {
        install(DefaultRequest) {
            headers.append("Content-Type", "application/json")
        }
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Ktor", message)
                }
            }
            level = LogLevel.ALL
        }
        engine {
            connectTimeout = 100_000
            socketTimeout = 1000_000
        }
        install(Logging) {
            install(Logging)
        }
    }

    suspend fun getPost(): List<Post> {
        return client.get {
            url("https://jsonplaceholder.typicode.com/posts")
        }
    }
}