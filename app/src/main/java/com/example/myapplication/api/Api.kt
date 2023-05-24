package com.example.myapplication.api

import com.example.myapplication.utils.Constants.API_KEY
import com.example.myapplication.utils.Constants.SEARCH_TERM
import com.example.myapplication.model.Video
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("videos/")
    suspend fun getVideo(
        @Query("key") key: String = API_KEY,
        @Query("q") searchTerm: String = SEARCH_TERM
    ) : Response<Video>

}