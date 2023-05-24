package com.example.myapplication.repository

import com.example.myapplication.api.RetrofitInstance

class VideoRepository {

    suspend fun getVideo(type: String = "yellow flowers") = RetrofitInstance.api.getVideo(searchTerm = type)

}