@file:Suppress("DEPRECATION")

package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemVideoBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class VideoAdapter(private val videoUrls: List<String>) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder?>() {

    private var currentPlayingPosition = 0
    private var isUserScrolling = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoUrl = videoUrls[position]
        holder.bindVideo(videoUrl, position)
    }

    override fun getItemCount(): Int {
        return videoUrls.size
    }

    inner class VideoViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {

        private var exoPlayer: SimpleExoPlayer? = null

        fun bindVideo(videoUrl: String?, position: Int) {
            if (exoPlayer == null) {
                exoPlayer = SimpleExoPlayer.Builder(binding.root.context).build()
                binding.playerView.player = exoPlayer
            }

            val mediaItem: MediaItem = MediaItem.fromUri(videoUrl!!)
            exoPlayer!!.setMediaItem(mediaItem)

            exoPlayer!!.prepare()
            exoPlayer!!.playWhenReady = position == currentPlayingPosition && !isUserScrolling
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCurrentPlayingPosition(currentPlayingPosition: Int, isUserScrolling: Boolean) {
        this.currentPlayingPosition = currentPlayingPosition
        this.isUserScrolling = isUserScrolling
        notifyDataSetChanged()
    }
}
