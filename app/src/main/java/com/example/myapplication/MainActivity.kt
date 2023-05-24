package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.myapplication.adapter.VideoAdapter
import com.example.myapplication.model.Hit
import com.example.myapplication.repository.VideoRepository
import com.example.myapplication.utils.Resource
import com.example.myapplication.viewmodel.VideoViewModel
import com.example.myapplication.viewmodel.ViewModelFactory
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var viewModel: VideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = VideoRepository()
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[VideoViewModel::class.java]

        getVideos()
    }

    private fun getVideos() {

        binding.progressBar.visibility = View.VISIBLE
        viewModel.getVideos()
        viewModel.video.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    setupRecyclerView(response.data!!.hits)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("error", response.message.toString())
                }
                is Resource.Loading -> {
                    Log.d("loading", "in loading state")
                }
            }
        }

    }

    private fun setupRecyclerView(hits: List<Hit>) {

        val list = ArrayList<String>()

        for (items in hits) list.add(items.videos.medium.url)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvVideos)

        binding.rvVideos.apply {
            videoAdapter = VideoAdapter(list)
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)

            addOnScrollListener(object : OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    val isUserScrolling = newState == RecyclerView.SCROLL_STATE_DRAGGING
                    val manager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisiblePosition = manager.findFirstCompletelyVisibleItemPosition()
                    videoAdapter.setCurrentPlayingPosition(firstVisiblePosition, isUserScrolling)
                }
            })
        }
    }
}