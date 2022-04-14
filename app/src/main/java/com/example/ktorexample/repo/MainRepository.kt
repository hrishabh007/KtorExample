package com.example.ktorexample.repo


import com.example.ktorexample.model.Post
import com.example.ktorexample.network.ApiClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject

class MainRepository @Inject constructor(private  val apiClass: ApiClass) {
    fun getPost(): Flow<List<Post>> = flow {
        emit(apiClass.getPost())
    }.flowOn(Dispatchers.IO)
}