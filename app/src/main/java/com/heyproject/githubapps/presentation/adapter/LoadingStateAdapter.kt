package com.heyproject.githubapps.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heyproject.githubapps.R
import com.heyproject.githubapps.databinding.ItemLoadingBinding

/**
Written by Yayan Rahmat Wijaya on 11/13/2022 16:37
Github : https://github.com/yayanrw
 **/

class LoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, loadState: LoadState
    ): LoadingStateViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding, retry)
    }


    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadingStateViewHolder(private val binding: ItemLoadingBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                val isLimitReach = loadState.error.localizedMessage?.contains("403") ?: false
                if (isLimitReach) {
                    binding.errorMsg.text =
                        binding.root.context.getString(R.string.reach_maximum_request)
                } else {
                    binding.errorMsg.text = loadState.error.localizedMessage
                }
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }
    }
}