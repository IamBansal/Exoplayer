package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.VideoRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private var repository: VideoRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideoViewModel(repository) as T
    }

}
