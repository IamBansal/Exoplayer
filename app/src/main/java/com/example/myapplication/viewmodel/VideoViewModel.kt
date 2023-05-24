package com.example.myapplication.viewmodel

import android.util.Log
import com.example.myapplication.model.Video
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.VideoRepository
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.launch

class VideoViewModel(private val repository: VideoRepository) : ViewModel(){

    val video: MutableLiveData<Resource<Video>> = MutableLiveData()

    fun getVideos() = viewModelScope.launch {
        getSafeVideo()
    }

    private suspend fun getSafeVideo() {

        video.postValue(Resource.Loading())
        try {
            val response = repository.getVideo()
            Log.d("response", response.toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    video.postValue(Resource.Success(it))
                }
            } else {
                video.postValue(Resource.Error(response.message()))
            }
        } catch (t: Throwable) {
            video.postValue(Resource.Error(t.message.toString()))
        }

    }

}