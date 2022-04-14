package com.example.ktorexample.util

import com.example.ktorexample.model.Post

sealed class ApiState {
    object Empty : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    class Sucess(val data: List<Post>) : ApiState()
    object Loading : ApiState()
}